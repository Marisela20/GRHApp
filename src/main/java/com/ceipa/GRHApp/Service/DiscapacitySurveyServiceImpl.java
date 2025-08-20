package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.*;
import com.ceipa.GRHApp.Mapper.DiscapacitySurveyMapper;
import com.ceipa.GRHApp.Mapper.UserMapper;
import com.ceipa.GRHApp.Model.*;
import com.ceipa.GRHApp.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceipa.GRHApp.Repository.DiscapacityResponseRepository;


import java.sql.Timestamp;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class DiscapacitySurveyServiceImpl implements DiscapacitySurveyService {

    @Autowired
    private DiscapacityQuestionRepository discapacityQuestionRepository;

    @Autowired
    private DiscapacitySurveyRepository discapacitySurveyRepository;

    @Autowired
    private DiscapacityAnswerOptionRepository discapacityAnswerOptionRepository;

    @Autowired
    private DiscapacityResponseRepository discapacityResponseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscapacityQuestionRepository questionRepository;

    @Autowired
    private DiscapacitySurveyMapper discapacitySurveyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscapacityAnswerOptionRepository optionRepository;
     private static final int TEMPLATE_SURVEY_ID = 6;
    private static final Logger log = LoggerFactory.getLogger(DiscapacitySurveyServiceImpl.class);

    private static final int DIAGNOSTIC_ID = 3;


    @Override
    public List<DiscapacityQuestion> getQuestions(int surveyId) {
        Optional<DiscapacitySurveyEntity> surveyOpt = discapacitySurveyRepository.findById(surveyId);
        if (!surveyOpt.isPresent()) return new ArrayList<>();

        DiscapacitySurveyEntity survey = surveyOpt.get();

        List<DiscapacityQuestionEntity> entities = questionRepository
                .findBySurvey_DiagnosticIdOrderBySectionAscOrderNumAsc(survey.getDiagnosticId());

        List<DiscapacityResponseEntity> allResponses = discapacityResponseRepository.findBySurveyId(surveyId);

        return entities.stream()
                .map(entity -> toModelWithResponses(entity, allResponses))
                .collect(Collectors.toList());
    }
    @Override
    public List<DiscapacityQuestion> getQuestionsBySurveyAndSection(int surveyId, int section) {
        // Buscar la encuesta del usuario
        DiscapacitySurveyEntity userSurvey = discapacitySurveyRepository.findById(surveyId).orElse(null);

        if (userSurvey == null) {
            return new ArrayList<>();
        }

        List<DiscapacityQuestionEntity> questionEntities =
                discapacityQuestionRepository.findBySurvey_IdAndSectionOrderByOrderNumAsc(surveyId, section);

        return questionEntities.stream().map(entity -> {
            DiscapacityQuestion question = new DiscapacityQuestion();
            question.setId(entity.getId());
            question.setDescription(entity.getQuestionText());
            question.setSection(entity.getSection());
            question.setOrder(entity.getOrderNum());
            question.setQuestionType(entity.getQuestionType());
            question.setSurveyId(userSurvey.getId());

            List<DiscapacityAnswerOptionEntity> options =
                    discapacityAnswerOptionRepository.findByQuestion_Id(entity.getId());

            List<DiscapacityAnswerOption> answerOptions = options.stream().map(opt -> {
                DiscapacityAnswerOption dto = new DiscapacityAnswerOption();
                dto.setId(opt.getId());
                dto.setText(opt.getText());
                dto.setScore(opt.getScore());
                return dto;
            }).collect(Collectors.toList());

            question.setAnswerOptions(answerOptions);
            return question;
        }).collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public Map<Integer, List<Integer>> getResponsesGroupedByQuestion(int surveyId) {
        // Trae todas las respuestas de la encuesta
        List<DiscapacityResponseEntity> responses = discapacityResponseRepository.findBySurveyId(surveyId);

        if (responses == null || responses.isEmpty()) {
            return Collections.emptyMap();
        }

        // Agrupa por ID de pregunta, mapeando SOLO opciones válidas (evita NPE)
        return responses.stream()
                .filter(Objects::nonNull)
                .filter(r -> r.getQuestion() != null)          // evita r.getQuestion().getId() NPE
                .filter(r -> r.getAnswerOption() != null)      // ignora respuestas con texto libre
                .collect(Collectors.groupingBy(
                        r -> r.getQuestion().getId(),
                        Collectors.mapping(
                                r -> r.getAnswerOption().getId(),
                                Collectors.toList()
                        )
                ));
    }

    private DiscapacityQuestion toModelWithResponses(DiscapacityQuestionEntity entity, List<DiscapacityResponseEntity> responses) {
        DiscapacityQuestion model = new DiscapacityQuestion();
        model.setId(entity.getId());
        model.setDescription(entity.getQuestionText());
        model.setSection(entity.getSection());
        model.setOrder(entity.getOrderNum());
        model.setQuestionType(entity.getQuestionType());

        if (entity.getSurvey() != null) {
            model.setSurveyId(entity.getSurvey().getId());
        }

        // ✅ cargar opciones (CORREGIDO)
        List<DiscapacityAnswerOptionEntity> optionsList = discapacityAnswerOptionRepository.findByQuestion(entity);
        List<DiscapacityAnswerOption> options = optionsList.stream()
                .map(opt -> new DiscapacityAnswerOption(opt.getId(), opt.getText(), opt.getScore()))
                .collect(Collectors.toList());
        model.setAnswerOptions(options);

        // ✅ cargar seleccionadas
        List<DiscapacityAnswerOption> selected = responses.stream()
                .filter(resp -> resp.getQuestion().getId() == entity.getId() && resp.getAnswerOption() != null)
                .map(resp -> new DiscapacityAnswerOption(
                        resp.getAnswerOption().getId(),
                        resp.getAnswerOption().getText(),
                        resp.getAnswerOption().getScore()
                ))
                .collect(Collectors.toList());

        model.setSelectedOptions(selected);

        return model;
    }


    private DiscapacityQuestion toModel(DiscapacityQuestionEntity entity) {
        DiscapacityQuestion model = new DiscapacityQuestion();
        model.setId(entity.getId());
        model.setDescription(entity.getQuestionText()); // ✅ campo correcto
        model.setSection(entity.getSection());
        model.setOrder(entity.getOrderNum()); // cuidado: en el modelo es 'order', en la entidad 'orderNum'
        model.setQuestionType(entity.getQuestionType());

        if (entity.getSurvey() != null) {
            model.setSurveyId(entity.getSurvey().getId());
        }

        if (entity.getOptions() != null) {
            model.setAnswerOptions(
                    entity.getOptions().stream().map(opt -> {
                        DiscapacityAnswerOption optionModel = new DiscapacityAnswerOption();
                        optionModel.setId(opt.getId());
                        optionModel.setText(opt.getText());
                        optionModel.setScore(opt.getScore());
                        return optionModel;
                    }).collect(Collectors.toList())
            );
        }

        return model;
    }

    @Override
    public List<DiscapacityQuestion> getQuestionsBySurveyId(int surveyId) {
        List<DiscapacityQuestionEntity> questionEntities = discapacityQuestionRepository.findBySurvey_Id(surveyId);
        List<DiscapacityQuestion> questions = new ArrayList<>();

        for (DiscapacityQuestionEntity entity : questionEntities) {
            DiscapacityQuestion question = new DiscapacityQuestion();
            question.setId(entity.getId());
            question.setDescription(entity.getQuestionText());
            question.setSection(entity.getSection());
            question.setOrder(entity.getOrderNum());
            question.setQuestionType(entity.getQuestionType());
            question.setSurveyId(surveyId);

            // ✅ Carga las opciones de respuesta correctamente
            List<DiscapacityAnswerOptionEntity> optionEntities =
                    discapacityAnswerOptionRepository.findByQuestion_Id(entity.getId());


            List<DiscapacityAnswerOption> options = optionEntities.stream().map(optionEntity -> {
                DiscapacityAnswerOption option = new DiscapacityAnswerOption();
                option.setId(optionEntity.getId());
                option.setText(optionEntity.getText());  // ← CORREGIDO
                option.setScore(optionEntity.getScore());
                return option;
            }).collect(Collectors.toList());

            question.setAnswerOptions(options);
            questions.add(question);
        }

        return questions;
    }
    @Override
    public List<DiscapacityQuestion> getSurveyQuestionsBySurveyId(int surveyId) {
        List<DiscapacityQuestionEntity> entities = discapacityQuestionRepository.findBySurvey_Id(surveyId);
        return convertEntitiesToModel(entities);
    }

    private List<DiscapacityQuestion> convertEntitiesToModel(List<DiscapacityQuestionEntity> entities) {
        return entities.stream().map(entity -> {
            DiscapacityQuestion dto = new DiscapacityQuestion();
            dto.setId(entity.getId());
            dto.setDescription(entity.getQuestionText());
            dto.setSection(entity.getSection());
            dto.setOrder(entity.getOrderNum());
            dto.setQuestionType(entity.getQuestionType());
            dto.setSurveyId(entity.getSurvey().getId());

            List<DiscapacityAnswerOptionEntity> options =
                    discapacityAnswerOptionRepository.findByQuestion_Id(entity.getId());

            List<DiscapacityAnswerOption> modelOptions = options.stream().map(opt -> {
                DiscapacityAnswerOption o = new DiscapacityAnswerOption();
                o.setId(opt.getId());
                o.setText(opt.getText());
                o.setScore(opt.getScore());
                return o;
            }).collect(Collectors.toList());

            dto.setAnswerOptions(modelOptions);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DiscapacityQuestion> getSurveyQuestionsBySurveyIdAndSection(int surveyId, int section) {
        List<DiscapacityQuestionEntity> entities =
                discapacityQuestionRepository.findBySurvey_IdAndSectionOrderByOrderNumAsc(surveyId, section);
        return convertEntitiesToModel(entities);
    }

    @Override
    public void deleteBySurveyId(int surveyId) {
        List<DiscapacityResponseEntity> responses = discapacityResponseRepository.findBySurvey_Id(surveyId);
        discapacityResponseRepository.deleteAll(responses);

        List<DiscapacityResponseEntity> prevResponses = discapacityResponseRepository.findBySurvey_Id(surveyId);
        discapacityResponseRepository.deleteAll(prevResponses); // ✅ Esto sí existe y funciona



        Optional<DiscapacitySurveyEntity> optionalSurvey = discapacitySurveyRepository.findById(surveyId);
        optionalSurvey.ifPresent(discapacitySurveyRepository::delete);
    }


    private DiscapacityQuestion convertToModel(DiscapacityQuestionEntity entity) {
        DiscapacityQuestion model = new DiscapacityQuestion();

        model.setId(entity.getId());
        model.setDescription(entity.getQuestionText()); // ✅ CAMBIO AQUÍ
        model.setSection(entity.getSection());
        model.setOrder(entity.getOrderNum());
        model.setQuestionType(entity.getQuestionType());

        if (entity.getSurvey() != null) {
            model.setSurveyId(entity.getSurvey().getId());
        }

        // Mapear opciones de respuesta
        if (entity.getOptions() != null) {
            List<DiscapacityAnswerOption> options = entity.getOptions()
                    .stream()
                    .map(optionEntity -> {
                        DiscapacityAnswerOption option = new DiscapacityAnswerOption();
                        option.setId(optionEntity.getId());
                        option.setText(optionEntity.getText());
                        option.setScore(optionEntity.getScore());
                        return option;
                    })
                    .collect(Collectors.toList());
            model.setAnswerOptions(options);
        }

        return model;
    }


    @Override
    public void saveResponses(int surveyId, Map<String, String> formData) {
        Optional<DiscapacitySurveyEntity> optionalSurvey = discapacitySurveyRepository.findById(surveyId);
        if (!optionalSurvey.isPresent()) return;
        DiscapacitySurveyEntity survey = optionalSurvey.get();

        List<DiscapacityQuestionEntity> questions = discapacityQuestionRepository.findBySurvey_Id(surveyId);

        for (DiscapacityQuestionEntity question : questions) {
            String questionKey = "question_" + question.getId();
            String manualKey = "manual_" + question.getId();

            String selectedOptionId = formData.get(questionKey);
            String manualText = formData.get(manualKey);

            // ✅ Solo guardar si hay algo
            if ((selectedOptionId != null && !selectedOptionId.isEmpty()) || (manualText != null && !manualText.isEmpty())) {

                // 🔍 Verificar si ya existe respuesta previa para esta pregunta
                Optional<DiscapacityResponseEntity> existing = discapacityResponseRepository
                        .findTopBySurveyIdAndQuestionIdOrderByCreatedAtDesc(surveyId, question.getId());

                DiscapacityResponseEntity response = existing.orElse(new DiscapacityResponseEntity());

                response.setSurvey(survey);
                response.setQuestion(question);
                response.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                // ✅ Opción seleccionada
                if (selectedOptionId != null && !selectedOptionId.isEmpty()) {
                    int optionId = Integer.parseInt(selectedOptionId);
                    discapacityAnswerOptionRepository.findById(optionId).ifPresent(response::setAnswerOption);
                }

                // ✅ Texto manual
                if (manualText != null && !manualText.trim().isEmpty()) {
                    response.setManualText(manualText.trim());
                }

                // 💾 Guardar (actualiza si ya existía, crea si no)
                discapacityResponseRepository.save(response);
            }
        }
    }

    @Override
    public Map<Integer, List<Integer>> getUserResponsesMap(int surveyId) {
        List<DiscapacityResponseEntity> responses = discapacityResponseRepository.findBySurveyId(surveyId);
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (DiscapacityResponseEntity resp : responses) {
            int qId = resp.getQuestion().getId();
            int aId = resp.getAnswerOption() != null ? resp.getAnswerOption().getId() : -1;

            if (!map.containsKey(qId)) {
                map.put(qId, new ArrayList<>());
            }
            if (aId != -1) {
                map.get(qId).add(aId);
            }
        }

        return map;
    }

    @Override
    public List<DiscapacityQuestion> getDiscapacitySurveyItem(Survey survey, int section) {
        if (survey == null || survey.getId() == 0) {
            return new ArrayList<>();
        }

        List<DiscapacityQuestionEntity> entities = discapacityQuestionRepository
                .findBySurvey_IdAndSectionOrderByOrderNumAsc(survey.getId(), section);

        return entities.stream()
                .map(this::convertToDiscapacityQuestionModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscapacityQuestion> getDiscapacitySurveyItemByUser(UserEntity user, int section) {
        Optional<DiscapacitySurveyEntity> userSurveyOpt =
                discapacitySurveyRepository.findTopByUserAccountAndDiagnosticIdOrderByCreatedAtDesc(user, 3);

        if (!userSurveyOpt.isPresent()) {
            return new ArrayList<>();
        }

        DiscapacitySurveyEntity userSurvey = userSurveyOpt.get();

        List<DiscapacityQuestionEntity> entities = discapacityQuestionRepository
                .findBySurvey_IdAndSectionOrderByOrderNumAsc(userSurvey.getId(), section);

        return entities.stream()
                .map(this::convertToDiscapacityQuestionModel)
                .collect(Collectors.toList());
    }



    @Override
    public List<DiscapacityQuestion> getQuestionsByOrderRange(int startOrder, int endOrder) {
        int fixedSurveyId = 3;
        return discapacityQuestionRepository.findBySurveyIdAndOrderRange(fixedSurveyId, startOrder, endOrder).stream()
                .map(this::mapToDiscapacityQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Survey> discapacitySurveyList() {
        return discapacitySurveyRepository.findAll().stream()
                .map(this::convertToSurveyModel)
                .collect(Collectors.toList());
    }

    @Override
    public int createNewDiscapacitySurveyForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ✅ Cambiado a findByUsernameIgnoreCase
        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));

        DiscapacitySurveyEntity newSurvey = new DiscapacitySurveyEntity();
        newSurvey.setUserAccount(user);
        newSurvey.setDiagnosticId(3);
        newSurvey.setTitle("Encuesta de Inclusión Laboral");
        newSurvey.setCompletedStatus(false);
        newSurvey.setCreatedAt(new Date());

        return discapacitySurveyRepository.save(newSurvey).getId();
    }


    @Override
    public Survey getSurveyById(int surveyId) {
        Optional<DiscapacitySurveyEntity> entityOpt = discapacitySurveyRepository.findById(surveyId);
        return entityOpt.map(discapacitySurveyMapper::toModel).orElse(null);
    }

    @Override
    public Survey convertToSurveyModel(DiscapacitySurveyEntity entity) {
        Survey model = new Survey();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setDiagnosticId(entity.getDiagnosticId());
        model.setCompletedStatus(entity.getCompletedStatus());
        model.setCreatedAt(new Timestamp(entity.getCreatedAt().getTime()));

        List<DiscapacityQuestionEntity> questionEntities = discapacityQuestionRepository.findBySurvey_Id(entity.getId());
        List<DiscapacityQuestion> questions = questionEntities.stream().map(this::mapToDiscapacityQuestion).collect(Collectors.toList());

        model.setQuestions(questions);
        return model;
    }


    @Override
    public List<DiscapacityResponse> convertEntitiesToModels(List<DiscapacityResponseEntity> entities) {
        return entities.stream().map(entity -> {
            DiscapacityResponse response = new DiscapacityResponse();
            response.setCreatedAt(entity.getCreatedAt());

            if (entity.getAnswerOption() != null) {
                DiscapacityAnswerOptionEntity answerEntity = entity.getAnswerOption();
                DiscapacityAnswerOption answerModel = new DiscapacityAnswerOption(
                        answerEntity.getId(),
                        answerEntity.getText(),
                        answerEntity.getScore()
                );
                response.setAnswerOption(answerModel);
            }

            if (entity.getManualText() != null) {
                response.setManualAnswer(entity.getManualText());
            }

            if (entity.getQuestion() != null) {
                DiscapacityQuestionEntity questionEntity = entity.getQuestion();
                DiscapacityQuestion questionModel = new DiscapacityQuestion();
                questionModel.setId(questionEntity.getId());
                questionModel.setDescription(questionEntity.getQuestionText()); // ✅ CORREGIDO AQUÍ
                questionModel.setSection(questionEntity.getSection());
                questionModel.setOrder(questionEntity.getOrderNum());
                questionModel.setQuestionType(questionEntity.getQuestionType());
                response.setQuestion(questionModel);
            }

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public Survey getLastSurveyForCurrentUser(int diagnosticId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // ✅ Cambiado a findByUsernameIgnoreCase
        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario: " + username));

        Optional<DiscapacitySurveyEntity> entityOpt =
                discapacitySurveyRepository.findTopByUserAccountAndDiagnosticIdOrderByCreatedAtDesc(user, diagnosticId);

        return entityOpt.map(discapacitySurveyMapper::toModel).orElse(null);
    }

    @Override
    public void saveDiscapacitySurveyDetail(Map<String, String[]> parameterMap, int surveyId) {
        DiscapacitySurveyEntity survey = discapacitySurveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la encuesta con ID: " + surveyId));

        List<DiscapacityResponseEntity> responses = new ArrayList<>();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            if (key.startsWith("question_")) {
                try {
                    int questionId = Integer.parseInt(key.substring(9));
                    DiscapacityQuestionEntity question = discapacityQuestionRepository.findById(questionId)
                            .orElseThrow(() -> new IllegalArgumentException("No se encontró la pregunta con ID: " + questionId));

                    for (String value : values) {
                        DiscapacityAnswerOptionEntity answer = discapacityAnswerOptionRepository.findById(Integer.parseInt(value))
                                .orElse(null); // en caso de que sea null por manual

                        DiscapacityResponseEntity response = new DiscapacityResponseEntity();
                        response.setSurvey(survey);
                        response.setQuestion(question);
                        response.setAnswerOption(answer); // ✅ aquí el cambio correcto
                        response.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

                        responses.add(response);
                    }
                } catch (NumberFormatException ignored) {
                    // ignorar entradas inválidas
                }
            }

            // para entradas manuales tipo: manual_123
            else if (key.startsWith("manual_")) {
                try {
                    int questionId = Integer.parseInt(key.substring(7));
                    DiscapacityQuestionEntity question = discapacityQuestionRepository.findById(questionId)
                            .orElseThrow(() -> new IllegalArgumentException("No se encontró la pregunta manual con ID: " + questionId));

                    for (String manualText : values) {
                        DiscapacityResponseEntity response = new DiscapacityResponseEntity();
                        response.setSurvey(survey);
                        response.setQuestion(question);
                        response.setManualText(manualText); // ✅ correcto
                        response.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                        responses.add(response);
                    }

                } catch (NumberFormatException ignored) {
                    // ignorar entradas inválidas
                }
            }
        }

        discapacityResponseRepository.saveAll(responses);
    }


    @Override
    public void saveDiscapacityManualAnswer(int surveyId, int questionId, String freeText) {
        DiscapacitySurveyEntity survey = discapacitySurveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Encuesta no encontrada"));
        DiscapacityQuestionEntity question = discapacityQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
        DiscapacityResponseEntity response = new DiscapacityResponseEntity();
        response.setSurvey(survey);
        response.setQuestion(question);
        response.setManualText(freeText);
        response.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        discapacityResponseRepository.save(response);
    }
    @Override
    @Transactional
    public Survey findOrCreateSurveyForUser(UserAccount user) {
        int diagnosticId = 3; // Encuesta de Inclusión Laboral

        // 1. Buscar encuesta existente para el usuario
        Optional<DiscapacitySurveyEntity> existingSurveyOpt =
                discapacitySurveyRepository.findTopByUserAccountAndDiagnosticIdOrderByCreatedAtDesc(user.getEntity(), diagnosticId);

        if (existingSurveyOpt.isPresent()) {
            return discapacitySurveyMapper.toModel(existingSurveyOpt.get());
        }

        // 2. Buscar plantilla base sin usuario asignado
        Optional<DiscapacitySurveyEntity> templateOpt =
                discapacitySurveyRepository.findTopByDiagnosticIdAndUserAccountIsNullOrderByCreatedAtDesc(diagnosticId);

        if (!templateOpt.isPresent()) {
            throw new IllegalStateException("❌ No existe plantilla base para el diagnóstico " + diagnosticId);
        }

        DiscapacitySurveyEntity template = templateOpt.get();

        // ✅ Verificar ID de plantilla y preguntas
        System.out.println("➡️ Plantilla seleccionada con ID: " + template.getId());

        List<DiscapacityQuestionEntity> templateQuestions =
                discapacityQuestionRepository.findBySurvey_Id(template.getId());

        System.out.println("📋 Número de preguntas en plantilla: " + templateQuestions.size());

        // 3. Crear nueva encuesta personalizada para el usuario
        DiscapacitySurveyEntity newSurvey = new DiscapacitySurveyEntity();
        newSurvey.setName(template.getName());
        newSurvey.setDescription("Encuesta personalizada para el usuario " + user.getUsername());
        newSurvey.setTitle(template.getTitle());
        newSurvey.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newSurvey.setCompletedStatus(false);
        newSurvey.setDiagnosticId(diagnosticId);
        newSurvey.setUserAccount(user.getEntity());

        discapacitySurveyRepository.save(newSurvey);

        // 4. Clonar preguntas y sus opciones
        for (DiscapacityQuestionEntity templateQuestion : templateQuestions) {
            DiscapacityQuestionEntity newQuestion = new DiscapacityQuestionEntity();
            newQuestion.setQuestionText(templateQuestion.getQuestionText());
            newQuestion.setQuestionType(templateQuestion.getQuestionType());
            newQuestion.setSection(templateQuestion.getSection());
            newQuestion.setOrderNum(templateQuestion.getOrderNum());
            newQuestion.setSurvey(newSurvey);

            discapacityQuestionRepository.save(newQuestion);

            List<DiscapacityAnswerOptionEntity> templateOptions =
                    discapacityAnswerOptionRepository.findByQuestion_Id(templateQuestion.getId());

            for (DiscapacityAnswerOptionEntity option : templateOptions) {
                DiscapacityAnswerOptionEntity newOption = new DiscapacityAnswerOptionEntity();
                newOption.setText(option.getText());
                newOption.setScore(option.getScore());
                newOption.setQuestion(newQuestion);

                discapacityAnswerOptionRepository.save(newOption);
            }
        }

        // 5. Retornar el modelo mapeado
        return discapacitySurveyMapper.toModel(newSurvey);
    }

    private DiscapacityQuestion convertToDiscapacityQuestionModel(DiscapacityQuestionEntity entity) {
            DiscapacityQuestion model = new DiscapacityQuestion();
            model.setId(entity.getId());
            model.setDescription(entity.getQuestionText());
            model.setSection(entity.getSection());
            model.setOrder(entity.getOrderNum());
            model.setQuestionType(entity.getQuestionType());
            model.setSurveyId(entity.getSurvey().getId());

            List<DiscapacityAnswerOptionEntity> options =
                    discapacityAnswerOptionRepository.findByQuestion_Id(entity.getId());

            model.setAnswerOptions(
                    options.stream()
                            .map(opt -> {
                                com.ceipa.GRHApp.Model.DiscapacityAnswerOption o = new com.ceipa.GRHApp.Model.DiscapacityAnswerOption();
                                o.setId(opt.getId());
                                o.setText(opt.getText());
                                o.setScore(opt.getScore());
                                return o;
                            }).collect(Collectors.toList())
            );

            return model;
        }


    @Override
    @Transactional
    public void markSurveyAsCompleted(int surveyId) {
        Optional<DiscapacitySurveyEntity> opt = discapacitySurveyRepository.findById(surveyId);
        if (opt.isPresent()) {
            DiscapacitySurveyEntity survey = opt.get();
            survey.setCompletedStatus(true);
            discapacitySurveyRepository.save(survey);
        }
    }

    @Override
    @Transactional
    public void saveSurveyResponses(int surveyId, List<DiscapacityResponse> responses) {
        DiscapacitySurveyEntity survey = discapacitySurveyRepository.findById(surveyId).orElse(null);
        if (survey == null) return;

        // Eliminar respuestas anteriores
        discapacityResponseRepository.deleteAll(
                discapacityResponseRepository.findBySurveyId(surveyId)
        );

        List<DiscapacityResponseEntity> entities = new ArrayList<>();

        for (DiscapacityResponse model : responses) {
            if (model == null || model.getQuestion() == null || model.getQuestion().getId() == null || model.getQuestion().getId() == 0) {
                continue;
            }

            // Obtener la pregunta
            DiscapacityQuestionEntity question = discapacityQuestionRepository
                    .findById(model.getQuestion().getId())
                    .orElse(null);
            if (question == null) continue;

            // Crear la respuesta
            DiscapacityResponseEntity entity = new DiscapacityResponseEntity();
            entity.setSurvey(survey);
            entity.setQuestion(question);

            // Si es una opción seleccionada
            if (model.getAnswerOption() != null && model.getAnswerOption().getId() != null) {
                discapacityAnswerOptionRepository.findById(model.getAnswerOption().getId())
                        .ifPresent(entity::setAnswerOption);
            }
            // Si es una respuesta abierta (texto)
            else if (model.getManualAnswer() != null && !model.getManualAnswer().trim().isEmpty()) {
                entity.setManualText(model.getManualAnswer().trim());
            }

            entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            entities.add(entity);
        }

        // Guardar todas las respuestas
        discapacityResponseRepository.saveAll(entities);

        // Marcar la encuesta como completada
        survey.setCompletedStatus(true);
        discapacitySurveyRepository.save(survey);
    }

    @Override
    public List<DiscapacityResponseEntity> getResponsesBySurveyId(int surveyId) {
        return discapacityResponseRepository.findBySurveyId(surveyId);
    }

    @Override
    public boolean hasUserCompletedSurvey(int surveyId) {
        Optional<DiscapacitySurveyEntity> optionalSurvey = discapacitySurveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            DiscapacitySurveyEntity survey = optionalSurvey.get();
            List<DiscapacityResponseEntity> responses = discapacityResponseRepository.findBySurvey(survey);
            return responses != null && !responses.isEmpty() &&
                    survey.getCompletedStatus() != null && survey.getCompletedStatus();
        }
        return false;
    }
    @Override
    @Transactional
    public DiscapacitySurveyEntity getLastSurveyForCurrentUserOrCreate(String username) {
        // 1) Usuario autenticado
        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + username));

        // 2) ¿Ya existe encuesta para este diagnóstico?
        Optional<DiscapacitySurveyEntity> exist =
                Optional.ofNullable(
                        discapacitySurveyRepository.findTopByUserAccount_IdAndDiagnosticIdOrderByCreatedAtDesc(
                                user.getId(), DIAGNOSTIC_ID
                        )
                );
        if (exist.isPresent()) return exist.get();

        // 3) Obtener plantilla base (sin usuario asignado)
        DiscapacitySurveyEntity template = discapacitySurveyRepository
                .findTopByDiagnosticIdAndUserAccountIsNullOrderByCreatedAtDesc(DIAGNOSTIC_ID)
                .orElseThrow(() -> new IllegalStateException("No existe plantilla base para diagnóstico " + DIAGNOSTIC_ID));

        // 4) Crear nueva encuesta para el usuario
        DiscapacitySurveyEntity newSurvey = new DiscapacitySurveyEntity();
        newSurvey.setName(template.getName());
        newSurvey.setTitle(template.getTitle());
        newSurvey.setDescription("Encuesta asignada automáticamente al usuario");
        newSurvey.setDiagnosticId(DIAGNOSTIC_ID);
        newSurvey.setUserAccount(user);
        newSurvey.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        newSurvey.setCompletedStatus(false);
        newSurvey = discapacitySurveyRepository.save(newSurvey);

        // 5) Clonar preguntas de la plantilla (ordenadas por sección + orden)
        List<DiscapacityQuestionEntity> tplQs =
                discapacityQuestionRepository.findBySurvey_IdOrderBySectionAscOrderNumAsc(template.getId());

        if (tplQs.isEmpty()) {
            throw new IllegalStateException("La plantilla (" + template.getId() + ") no tiene preguntas.");
        }

        List<DiscapacityQuestionEntity> toSave = new ArrayList<>();
        for (DiscapacityQuestionEntity tq : tplQs) {
            DiscapacityQuestionEntity nq = new DiscapacityQuestionEntity();
            nq.setSurvey(newSurvey);
            nq.setQuestionText(tq.getQuestionText());
            nq.setQuestionType(tq.getQuestionType());
            nq.setSection(tq.getSection() == null ? 1 : tq.getSection());
            nq.setOrderNum(tq.getOrderNum());
            toSave.add(nq);
        }
        discapacityQuestionRepository.saveAll(toSave);

        // 6) Construir índice (section|orderNum) → id de la nueva pregunta
        Map<String, Integer> newIdByKey = new HashMap<>();
        List<DiscapacityQuestionEntity> newQs =
                discapacityQuestionRepository.findBySurvey_IdOrderBySectionAscOrderNumAsc(newSurvey.getId());

        for (DiscapacityQuestionEntity nq : newQs) {
            String key = String.valueOf(nq.getSection() == null ? 1 : nq.getSection())
                    + "|" + nq.getOrderNum();
            newIdByKey.put(key, nq.getId());
        }

        // 7) Clonar opciones de respuesta
        List<DiscapacityAnswerOptionEntity> allNewOpts = new ArrayList<>();
        for (DiscapacityQuestionEntity tq : tplQs) {
            String key = String.valueOf(tq.getSection() == null ? 1 : tq.getSection())
                    + "|" + tq.getOrderNum();
            Integer newQid = newIdByKey.get(key);
            if (newQid == null) continue;

            List<DiscapacityAnswerOptionEntity> opts =
                    discapacityAnswerOptionRepository.findByQuestion_Id(tq.getId());
            if (opts == null || opts.isEmpty()) continue;

            for (DiscapacityAnswerOptionEntity o : opts) {
                DiscapacityAnswerOptionEntity n = new DiscapacityAnswerOptionEntity();
                n.setQuestion(discapacityQuestionRepository.getById(newQid));
                n.setText(o.getText());
                n.setScore(o.getScore());
                allNewOpts.add(n);
            }
        }
        if (!allNewOpts.isEmpty()) {
            discapacityAnswerOptionRepository.saveAll(allNewOpts);
        }

        return newSurvey;
    }

    @Override
    @Transactional
    public void ensurePopulatedSurvey(int destSurveyId) {
        final int SRC = 170; // encuesta “golden” con 38 preguntas y opciones

        long qCount = discapacityQuestionRepository.countBySurvey_Id(destSurveyId);
        if (qCount < 38) {
            // 1) Limpiar destino si quedó incompleto
            List<Integer> qids = discapacityQuestionRepository.findIdsBySurveyId(destSurveyId);
            if (qids != null && !qids.isEmpty()) {
                discapacityAnswerOptionRepository.deleteByQuestion_IdIn(qids);
            }
            discapacityQuestionRepository.deleteBySurvey_Id(destSurveyId);

            // 2) Copiar preguntas por order_num con tu mapeo de secciones
            List<DiscapacityQuestionEntity> srcQs =
                    discapacityQuestionRepository.findBySurvey_IdOrderBySectionAscOrderNumAsc(SRC);
            DiscapacitySurveyEntity dst =
                    discapacitySurveyRepository.findById(destSurveyId)
                            .orElseThrow(() -> new IllegalArgumentException("Encuesta destino no existe: " + destSurveyId));

            Map<Integer, DiscapacityQuestionEntity> newByOrder = new HashMap<>();
            for (DiscapacityQuestionEntity s : srcQs) {
                DiscapacityQuestionEntity q = new DiscapacityQuestionEntity();
                q.setSurvey(dst);
                q.setOrderNum(s.getOrderNum());
                q.setQuestionText(s.getQuestionText());
                q.setSection(sectionFromOrderNum(s.getOrderNum())); // 1..7 según tu regla
                // copia aquí flags extra si existen (isRequired, tipo, etc.)
                q = discapacityQuestionRepository.save(q);
                newByOrder.put(s.getOrderNum(), q);
            }

            // 3) Copiar opciones usando order_num como llave natural
            for (DiscapacityQuestionEntity s : srcQs) {
                List<DiscapacityAnswerOptionEntity> opts =
                        discapacityAnswerOptionRepository.findByQuestion_Id(s.getId());
                if (opts == null || opts.isEmpty()) continue;

                DiscapacityQuestionEntity target = newByOrder.get(s.getOrderNum());
                for (DiscapacityAnswerOptionEntity o : opts) {
                    DiscapacityAnswerOptionEntity no = new DiscapacityAnswerOptionEntity();
                    no.setQuestion(target);
                    no.setText(o.getText());
                    no.setScore(o.getScore());
                    // copia orderNum/createdAt si tu tabla lo requiere
                    discapacityAnswerOptionRepository.save(no);
                }
            }
        } else {
            // Si ya tenía 38, solo normalizamos secciones
            applySectionMapping(destSurveyId);
        }
    }

    private int sectionFromOrderNum(int n) {
        if (n <= 17) return 1;
        if (n <= 19) return 2;
        if (n <= 23) return 3;
        if (n <= 26) return 4;
        if (n <= 30) return 5;
        if (n <= 34) return 6;
        return 7; // 35..38
    }



    @Transactional
    public void applySectionMapping(int surveyId) {
        List<DiscapacityQuestionEntity> qs = discapacityQuestionRepository.findBySurvey_Id(surveyId);
        for (DiscapacityQuestionEntity q : qs) {
            q.setSection(sectionFromOrderNum(q.getOrderNum()));
        }
        discapacityQuestionRepository.saveAll(qs);
    }


    private void cloneQuestionsFromTemplateSurvey(int diagnosticId, DiscapacitySurveyEntity targetSurvey) {
        List<DiscapacityQuestionEntity> existing = questionRepository.findBySurvey_Id(targetSurvey.getId());
        if (!existing.isEmpty()) return;

        Optional<DiscapacitySurveyEntity> templateOpt =
                discapacitySurveyRepository.findTopByDiagnosticIdOrderByCreatedAtAsc(diagnosticId);

        if (!templateOpt.isPresent()) return;

        DiscapacitySurveyEntity templateSurvey = templateOpt.get();

        List<DiscapacityQuestionEntity> templateQuestions = questionRepository.findBySurvey_Id(templateSurvey.getId());
        if (templateQuestions == null || templateQuestions.isEmpty()) return;

        Set<String> existingTexts = new HashSet<>();

        for (DiscapacityQuestionEntity original : templateQuestions) {
            // Prevenir duplicados por texto
            if (!existingTexts.add(original.getQuestionText())) continue;

            DiscapacityQuestionEntity copy = new DiscapacityQuestionEntity();
            copy.setSurvey(targetSurvey);
            copy.setQuestionText(original.getQuestionText());
            copy.setSection(original.getSection());
            copy.setOrderNum(original.getOrderNum());
            copy.setQuestionType(original.getQuestionType());

            DiscapacityQuestionEntity savedCopy = questionRepository.save(copy);

            List<DiscapacityAnswerOptionEntity> options = discapacityAnswerOptionRepository.findByQuestion(original);
            for (DiscapacityAnswerOptionEntity option : options) {
                DiscapacityAnswerOptionEntity newOption = new DiscapacityAnswerOptionEntity();
                newOption.setQuestion(savedCopy);
                newOption.setText(option.getText());
                newOption.setScore(option.getScore());
                discapacityAnswerOptionRepository.save(newOption);
            }
        }
    }



    private Survey convertToModel(DiscapacitySurveyEntity entity) {
        Survey survey = new Survey();
        survey.setId(entity.getId());
        survey.setName(entity.getName());
        survey.setDescription(entity.getDescription());
        survey.setCreatedAt(entity.getCreatedAt());
        survey.setDiagnosticId(entity.getDiagnosticId());
        return survey;
    }

    private DiscapacityQuestion mapToDiscapacityQuestion(DiscapacityQuestionEntity entity) {
        DiscapacityQuestion model = new DiscapacityQuestion();
        model.setId(entity.getId());
        model.setDescription(entity.getQuestionText()); // 🔧 aquí el cambio
        model.setSection(entity.getSection());
        model.setOrder(entity.getOrderNum());
        model.setQuestionType(entity.getQuestionType());

        List<DiscapacityAnswerOption> options =
                (entity.getOptions() != null && !entity.getOptions().isEmpty())
                        ? entity.getOptions().stream().map(this::convertOption).collect(Collectors.toList())
                        : discapacityAnswerOptionRepository.findByQuestion_Id(entity.getId())
                        .stream().map(this::convertOption).collect(Collectors.toList());

        model.setAnswerOptions(options);
        return model;
    }




    private DiscapacityAnswerOption convertOption(DiscapacityAnswerOptionEntity entity) {
        return new DiscapacityAnswerOption(entity.getId(), entity.getText(), entity.getScore());
    }
}
