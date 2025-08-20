package com.ceipa.GRHApp.Controller;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacityAnswerOptionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityQuestionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityResponseEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Mapper.DiscapacityQuestionMapper;
import com.ceipa.GRHApp.Mapper.DiscapacitySurveyMapper;
import com.ceipa.GRHApp.Mapper.UserMapper;
import com.ceipa.GRHApp.Model.*;
import com.ceipa.GRHApp.Repository.DiscapacityAnswerOptionRepository;
import com.ceipa.GRHApp.Repository.DiscapacityQuestionRepository;
import com.ceipa.GRHApp.Repository.DiscapacityResponseRepository;
import com.ceipa.GRHApp.Repository.DiscapacitySurveyRepository;
import com.ceipa.GRHApp.Repository.SurveyRepository;
import com.ceipa.GRHApp.Repository.UserRepository;
import com.ceipa.GRHApp.Service.DiscapacitySurveyService;
import com.ceipa.GRHApp.Service.SurveyItemExcludedService;
import com.ceipa.GRHApp.Service.SurveyService;
import com.ceipa.GRHApp.Util.SurveyExcelExporter;
import com.ceipa.GRHApp.Util.SurveyPdfExporter;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/survey")
public class SurveyController {

    @Autowired private SurveyService surveyService;
    @Autowired private DiscapacitySurveyService discapacitySurveyService;
    @Autowired private SurveyItemExcludedService surveyItemExcludedService;
    @Autowired private SurveyRepository surveyRepository;
    @Autowired private DiscapacitySurveyRepository discapacitySurveyRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SurveyExcelExporter surveyExcelExporter;
    @Autowired private DiscapacityQuestionRepository discapacityQuestionRepository;
    @Autowired private DiscapacityAnswerOptionRepository discapacityAnswerOptionRepository;
    @Autowired private DiscapacityResponseRepository discapacityResponseRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private DiscapacitySurveyMapper discapacitySurveyMapper;
    @Autowired private DiscapacityQuestionMapper discapacityQuestionMapper;

    // FORMULARIO ENCUESTA Personas +B o Engagement
    @RequestMapping("/surveyForm")
    public String surveyForm(@RequestParam(defaultValue = "0") int questionId,
                             @RequestParam boolean nextPage,
                             @RequestParam int diagnosticId,
                             Model model) {

        Survey survey = surveyService.getSurvey(diagnosticId);
        List<SurveyItem> surveyItemList = surveyService.getSurveyItems(survey, nextPage, questionId, diagnosticId);

        model.addAttribute("survey", survey);
        model.addAttribute("surveyQuestions", surveyItemList);

        if (!surveyItemList.isEmpty() && diagnosticId == 1) {
            questionId = surveyItemList.get(0).getId();
            model.addAttribute("subLevelQuestions", surveyService.getSubLevelQuestion(survey.getSurveyDetailList(), questionId));
            model.addAttribute("surveyItemExcludedList", surveyItemExcludedService.getSurveyItemExcludedList());
        }

        switch (diagnosticId) {
            case 1:
                return "survey/bPersonForm";
            case 2:
                return "survey/engagementForm";
            default:
                return "survey/surveyForm";
        }
    }

    // Guarda respuesta de encuesta (POST en lugar de @RequestMapping)
    @PostMapping("/saveSurvey")
    public String saveSurvey(@RequestParam int surveyId,
                             @RequestParam int questionId,
                             @RequestParam(required = false) Integer answerId,
                             @RequestParam int subQuestionId,
                             @RequestParam(required = false) Integer subAnswerId,
                             @RequestParam boolean nextPage,
                             @RequestParam int diagnosticId,
                             Model model) {
        model.addAttribute("survey", new Survey(surveyId));
        surveyService.saveSurveyDetail(surveyId, questionId, answerId, subQuestionId, subAnswerId);
        return surveyForm(questionId, nextPage, diagnosticId, model);
    }


    @RequestMapping("/closeSurvey")
    public String closeSurvey(@RequestParam int surveyId, Model model) {
        surveyService.closeSurvey(surveyId);
        return this.result(surveyId, model);
    }

    @PostMapping("/closeSurvey")
    public String closeSurveyAction(@RequestParam int surveyId) {
        int diagnosticId = surveyService.closeSurvey(surveyId);

        switch (diagnosticId) {
            case 1:
                return "redirect:/survey/bperson/surveyList";
            case 3:
                return "redirect:/survey/discapacity/surveyList";
            default:
                return "redirect:/dashboard";
        }
    }

    @RequestMapping("/result")
    public String result(@RequestParam int surveyId, Model model) {
        Survey survey = surveyService.getSurveyById(surveyId);
        if (survey.getDiagnosticId() == 1) {
            model.addAttribute("grhPractice", surveyService.grhPractice(surveyId));
            model.addAttribute("implementationLevels", surveyService.implementationLevels(surveyId));
            model.addAttribute("directTrait", surveyService.directTrait(surveyId));
            model.addAttribute("combinedTrait", surveyService.combinedTrait(surveyService.directTrait(surveyId)));
            model.addAttribute("organizationalCulture", surveyService.organizationalCulture(surveyId));
            return "survey/results";
        }
        return "survey/unknownResults";
    }

    // Export a Excel (solo anoto el parámetro)
    @GetMapping("/export")
    public String exportToExcel(HttpServletResponse response,
                                @RequestParam int diagnosticId) throws IOException {
        response.setContentType("application/octet-stream");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        response.setHeader("Content-Disposition",
                "attachment; filename=Reporte_Diagnostico_" + date + ".xlsx");
        surveyExcelExporter.export(response, diagnosticId);
        return "dashboard";
    }
    @GetMapping("/downloadPdf/{surveyId}")
    public void downloadSurveyResultPdf(@PathVariable int surveyId, HttpServletResponse response) throws IOException, DocumentException {
        List<DiscapacityResponse> responses = discapacitySurveyService.convertEntitiesToModels(
                discapacitySurveyService.getResponsesBySurveyId(surveyId));
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resultado_encuesta_" + surveyId + ".pdf");
        new SurveyPdfExporter(responses).export(response);
    }

    @RequestMapping("/surveyList")
    public String surveyList(@RequestParam(defaultValue = "1") int diagnosticId, Model model) {
        if (diagnosticId == 3) {
            // Inclusión Laboral: normalmente mostramos solo finalizadas
            model.addAttribute("surveyList",
                    discapacitySurveyRepository.findByDiagnosticIdAndCompletedStatus(3, true));
        } else {
            // Personas +B u otros
            model.addAttribute("surveyList", surveyService.surveyList(diagnosticId));
        }
        model.addAttribute("diagnosticId", diagnosticId);
        return "survey/surveyList";   // <- usa SIEMPRE la misma plantilla
    }

    // Personas +B  (muestra todas: finalizadas y en progreso)
    @GetMapping("/bperson/surveyList")
    public String bPersonSurveyList(Model model) {
        model.addAttribute("surveyList", surveyService.surveyList(1));   // ó surveyRepository.findByDiagnosticId(1)
        return "survey/surveyList";
    }

    // Inclusión Laboral (muestra SOLO completadas)
    @GetMapping("/discapacity/surveyList")
    public String discapacitySurveyList(Model model) {
        model.addAttribute("surveyList",
                discapacitySurveyRepository.findByDiagnosticIdAndCompletedStatus(3, true));
        return "survey/discapacitySurveyList";
    }


    @GetMapping("/inclusionLaboral/list")
    public String listInclusionLaboralDiagnoses(Model model) {
        List<DiscapacitySurveyEntity> completedSurveys = discapacitySurveyRepository.findByDiagnosticIdAndCompletedStatus(3, true);
        model.addAttribute("encuestas", completedSurveys);
        return "survey/listInclusionLaboral";
    }

    @GetMapping("/allSurveyList")
    public String allSurveyList(Model model) {

        // Personas +B
        java.util.List<com.ceipa.GRHApp.DatabaseEntity.SurveyEntity> bperson =
                surveyRepository.findAll();

        // Inclusión Laboral
        java.util.List<com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity> inclusion =
                discapacitySurveyRepository.findAll();

        // Une y ordena DESC por id
        java.util.List<Object> all = new java.util.ArrayList<>();
        all.addAll(bperson);
        all.addAll(inclusion);

        all.sort((a, b) -> {
            Integer idA = (a instanceof com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity)
                    ? ((com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity) a).getId()
                    : ((com.ceipa.GRHApp.DatabaseEntity.SurveyEntity) a).getId();
            Integer idB = (b instanceof com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity)
                    ? ((com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity) b).getId()
                    : ((com.ceipa.GRHApp.DatabaseEntity.SurveyEntity) b).getId();
            return Integer.compare(idB, idA); // DESC
        });

        model.addAttribute("surveys", all);
        return "survey/allSurveyList";
    }



    // Personas +B (diagnosticId = 1)
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteSurvey")
    public String deleteSurvey(@RequestParam int surveyId, RedirectAttributes ra) {
        surveyService.deleteSurvey(surveyId);
        ra.addFlashAttribute("success", "Diagnóstico eliminado.");
        return "redirect:/survey/bperson/surveyList";
    }

    // Inclusión Laboral (diagnosticId = 3) - borra en cascada
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/discapacitySurvey/delete/{id}")
    @Transactional
    public String deleteDiscapacitySurvey(@PathVariable int id, RedirectAttributes ra) {
        try {
            discapacityResponseRepository.deleteBySurvey_Id(id);
            List<Integer> qids = discapacityQuestionRepository.findIdsBySurveyId(id);
            if (qids != null && !qids.isEmpty()) {
                discapacityAnswerOptionRepository.deleteByQuestion_IdIn(qids);
            }
            discapacityQuestionRepository.deleteBySurvey_Id(id);
            discapacitySurveyRepository.deleteById(id);
            ra.addFlashAttribute("success", "Diagnóstico eliminado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/survey/discapacity/surveyList";
    }
    @GetMapping("/discapacitySurvey")
    public String showDiscapacitySurvey(@RequestParam("surveyId") int surveyId,
                                        @RequestParam(value = "section", defaultValue = "1") int section,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {

        // 0) Validación existencia
        java.util.Optional<DiscapacitySurveyEntity> surveyOpt = discapacitySurveyRepository.findById(surveyId);
        if (!surveyOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Encuesta no encontrada.");
            return "redirect:/dashboard";
        }

        // ✅ AUTOFIX: si la encuesta está “corta”, repoblarla; si no, normalizar secciones
        long total = discapacityQuestionRepository.countBySurvey_Id(surveyId);
        if (total < 38L) {
            discapacitySurveyService.ensurePopulatedSurvey(surveyId);
        } else {
            discapacitySurveyService.applySectionMapping(surveyId);
        }

        // 1) Preguntas de la sección solicitada (ya corregidas)
        java.util.List<DiscapacityQuestionEntity> questionEntities =
                discapacityQuestionRepository.findBySurvey_IdAndSectionOrderByOrderNumAsc(surveyId, section);

        // 2) Calcular maxSection (dinámico)
        java.util.List<DiscapacityQuestionEntity> allQs = discapacityQuestionRepository.findBySurvey_Id(surveyId);
        Integer maxSection = allQs.stream()
                .map(DiscapacityQuestionEntity::getSection)
                .filter(java.util.Objects::nonNull)
                .max(java.util.Comparator.naturalOrder())
                .orElse(1);
        model.addAttribute("maxSection", maxSection); // por si tu template lo usa
        model.addAttribute("ms", maxSection);         // alias opcional

        // 3) Mapear a modelo y opciones (igual que ya tenías)
        java.util.List<DiscapacityQuestion> questions = discapacityQuestionMapper.toModelList(questionEntities);

        for (DiscapacityQuestion q : questions) {
            java.util.List<DiscapacityAnswerOptionEntity> answerEntities =
                    discapacityAnswerOptionRepository.findByQuestion_Id(q.getId());

            java.util.List<DiscapacityAnswerOption> options = answerEntities.stream().map(e -> {
                DiscapacityAnswerOption o = new DiscapacityAnswerOption();
                o.setId(e.getId());
                o.setText(e.getText());
                o.setScore(e.getScore());
                return o;
            }).collect(java.util.stream.Collectors.toList());

            q.setAnswerOptions(options);
        }

        java.util.Map<Integer, java.util.List<Integer>> responsesByQuestionId =
                discapacitySurveyService.getResponsesGroupedByQuestion(surveyId);

        // 4) Enviar al modelo
        model.addAttribute("survey", surveyOpt.get());
        model.addAttribute("questions", questions);
        model.addAttribute("responsesByQuestionId", responsesByQuestionId);
        model.addAttribute("currentSection", section);
        model.addAttribute("totalQuestions", questions.size());

        return "survey/surveyDiscapacity";
    }



    @PostMapping("/saveDiscapacitySurvey")
    @Transactional
    public String saveDiscapacitySurvey(HttpServletRequest request,
                                        @RequestParam int surveyId,
                                        @RequestParam int diagnosticId,
                                        @RequestParam int currentSection,
                                        @RequestParam String action,
                                        RedirectAttributes redirectAttributes) {
        // Usuario
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOpt = userRepository.findByUsernameIgnoreCase(username);
        if (!userOpt.isPresent()) userOpt = userRepository.findByEmailIgnoreCase(username);
        if (!userOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/dashboard";
        }
        UserEntity user = userOpt.get();

        // Encuesta y preguntas de la sección
        DiscapacitySurveyEntity survey = discapacitySurveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Encuesta no encontrada"));
        List<DiscapacityQuestionEntity> questions =
                discapacityQuestionRepository.findBySurvey_IdAndSectionOrderByOrderNumAsc(surveyId, currentSection);

        // Guardar/actualizar BORRADORES de la sección actual
        for (DiscapacityQuestionEntity q : questions) {
            String sel = request.getParameter("question_" + q.getId());
            String txt = request.getParameter("manual_" + q.getId());
            boolean hasAnswer = (sel != null && !sel.isEmpty()) || (txt != null && !txt.trim().isEmpty());

            List<DiscapacityResponseEntity> drafts =
                    discapacityResponseRepository.findAllBySurvey_IdAndQuestion_IdAndUserAccount_IdAndIsDraft(
                            surveyId, q.getId(), user.getId(), true);

            if (!hasAnswer) {
                drafts.forEach(discapacityResponseRepository::delete);
                continue;
            }

            DiscapacityResponseEntity r = drafts.isEmpty() ? new DiscapacityResponseEntity() : drafts.get(0);
            r.setSurvey(survey);
            r.setQuestion(q);
            r.setUserAccount(user);
            r.setSection(currentSection);
            r.setIsDraft(true);
            r.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            r.setAnswerOption(null);
            r.setManualText(null);
            r.setAnswer(null);

            if (sel != null && !sel.isEmpty()) {
                try {
                    int optId = Integer.parseInt(sel);
                    discapacityAnswerOptionRepository.findById(optId).ifPresent(r::setAnswerOption);
                } catch (NumberFormatException ignored) {}
            }
            if (txt != null && !txt.trim().isEmpty()) r.setManualText(txt.trim());

            discapacityResponseRepository.save(r);
        }

        // Navegación
        if ("prev".equalsIgnoreCase(action)) {
            int prev = Math.max(1, currentSection - 1);
            return "redirect:/survey/discapacitySurvey?surveyId=" + surveyId + "&section=" + prev;
        }
        if ("next".equalsIgnoreCase(action)) {
            int next = Math.min(7, currentSection + 1);
            return "redirect:/survey/discapacitySurvey?surveyId=" + surveyId + "&section=" + next;
        }

        // finish: publicar borradores y completar
        discapacityResponseRepository.publishDrafts(surveyId, user.getId());
        survey.setCompletedStatus(true);
        discapacitySurveyRepository.save(survey);
        redirectAttributes.addFlashAttribute("success", "¡Encuesta completada con éxito!");
        return "redirect:/survey/viewResult/" + surveyId;
    }


    @PostMapping("/discapacitySurvey/submit")
    public String submitSurvey(@RequestParam Map<String, String> formData) {
        try {
            UserAccount user = getCurrentUserAccount();
            if (user == null) return "redirect:/login";

            Survey survey = discapacitySurveyService.findOrCreateSurveyForUser(user);
            discapacitySurveyService.saveResponses(survey.getId(), formData);
            discapacitySurveyService.markSurveyAsCompleted(survey.getId());

            return "redirect:/survey/completed";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/discapacitySurvey/start")
    public String startDiscapacitySurvey(RedirectAttributes redirectAttributes) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("🔁 Usuario autenticado: " + username);

            DiscapacitySurveyEntity surveyEntity =
                    discapacitySurveyService.getLastSurveyForCurrentUserOrCreate(username);

            if (surveyEntity == null || surveyEntity.getId() <= 0) {
                redirectAttributes.addFlashAttribute("error", "No se pudo generar la encuesta.");
                return "redirect:/dashboard";
            }

            // Validaciones/correcciones de consistencia fuera de @Transactional del controller
            discapacitySurveyService.ensurePopulatedSurvey(surveyEntity.getId());

            System.out.println("✅ Encuesta generada ID: " + surveyEntity.getId());
            return "redirect:/survey/discapacitySurvey?surveyId=" + surveyEntity.getId() + "&section=1";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al iniciar encuesta: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }


    @GetMapping({
            "/exportPDF", "/exportPdf", "/downloadPdf", "/export/pdf",              // ?surveyId=...
            "/exportPDF/{surveyId}", "/exportPdf/{surveyId}", "/downloadPdf/{surveyId}", "/export/pdf/{surveyId}"
    })
    public void exportSurveyPdf(
            @PathVariable(value = "surveyId", required = false) Integer surveyIdPath,
            @RequestParam(value = "surveyId", required = false) Integer surveyIdQuery,
            HttpServletResponse response) throws IOException {

        int surveyId = (surveyIdPath != null) ? surveyIdPath
                : (surveyIdQuery != null ? surveyIdQuery : -1);

        if (surveyId <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Falta el parámetro surveyId.");
            return;
        }

        try {
            // Obtén las respuestas y arma el PDF
            List<DiscapacityResponseEntity> entities =
                    discapacitySurveyService.getResponsesBySurveyId(surveyId);
            List<DiscapacityResponse> responses =
                    discapacitySurveyService.convertEntitiesToModels(entities);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=resultado_encuesta_" + surveyId + ".pdf");

            new SurveyPdfExporter(responses).export(response);

        } catch (Exception e) {
            response.reset();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("No se pudo generar el PDF. "
                    + e.getClass().getSimpleName()
                    + (e.getMessage() != null ? (": " + e.getMessage()) : ""));
            response.getWriter().flush();
        }
    }


    @GetMapping("/viewResult/{surveyId}")
    public String viewSurveyResult(@PathVariable int surveyId, Model model) {
        List<DiscapacityQuestionEntity> questionEntities = discapacityQuestionRepository.findBySurvey_Id(surveyId);
        List<DiscapacityResponseEntity> responseEntities = discapacityResponseRepository.findBySurveyId(surveyId);

        Map<Integer, String> answerMap = responseEntities.stream().collect(Collectors.toMap(
                r -> r.getQuestion().getId(),
                r -> r.getAnswerOption() != null ? r.getAnswerOption().getText() : r.getManualText(),
                (existing, replacement) -> existing
        ));

        List<QuestionWithAnswer> resultList = questionEntities.stream().map(q -> {
            QuestionWithAnswer qa = new QuestionWithAnswer();
            qa.setQuestionText(q.getQuestionText());
            qa.setAnswerText(answerMap.getOrDefault(q.getId(), "Sin responder"));
            return qa;
        }).collect(Collectors.toList());

        model.addAttribute("questionWithAnswers", resultList);
        model.addAttribute("surveyId", surveyId);
        return "survey/surveyDiscapacityResult";
    }

    // ---------- Helpers ----------

    private UserAccount getCurrentUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();

            // Busca primero por username ignore-case y, si no, por email ignore-case
            Optional<UserEntity> entityOpt = userRepository.findByUsernameIgnoreCase(username);
            if (!entityOpt.isPresent()) {
                entityOpt = userRepository.findByEmailIgnoreCase(username);
            }
            return entityOpt.map(userMapper::userEntityToUser).orElse(null);
        }
        return null;
    }
}
