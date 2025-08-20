package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.*;
import com.ceipa.GRHApp.Mapper.*;
import com.ceipa.GRHApp.Model.*;
import com.ceipa.GRHApp.Repository.*;
import com.ceipa.GRHApp.Util.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.ceipa.GRHApp.Model.SurveyAnswerOption;
import com.ceipa.GRHApp.DatabaseEntity.SurveyAnswerOptionEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.IntStream;

import static com.ceipa.GRHApp.Model.UtilNames.*;

@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService {

    private static final int B_PERSON_DIAGNOSTIC = 1;
    private static final int ENGAGEMENT_DIAGNOSTIC = 2;
    private static final int DISCAPACITY_DIAGNOSTIC = 3;
    @Autowired
    private SurveyItemservice surveyItemservice;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyDetailRepository surveyDetailRepository;

    @Autowired
    private SurveyItemExcludedService surveyItemExcludedService;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private ClassificationMapper classificationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ConclusionService conclusionService;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private SurveyDetailMapper surveyDetailMapper;
    @Autowired
    private SurveyItemRepository surveyItemRepository;
    @Autowired
    private SurveyAnswerOptionRepository surveyAnswerOptionRepository;
    @Autowired
    private DiscapacityQuestionRepository discapacityQuestionRepository;
    @Autowired
    private DiscapacitySurveyRepository discapacitySurveyRepository;

    @Autowired
    private DiscapacityResponseRepository discapacityResponseRepository;

    @Autowired
    private DiscapacityAnswerOptionRepository discapacityAnswerOptionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscapacitySurveyService discapacitySurveyService;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Survey getSurvey(int diagnosticId) {
        UserAccount currentUserAccount = userService.getCurrentUserInfo();

        if (diagnosticId == 3) {
            // ¿Ya existe una encuesta de discapacidad para este usuario?
            DiscapacitySurveyEntity existing = discapacitySurveyRepository
                    .findAll()
                    .stream()
                    .filter(e -> e.getUserAccount() != null
                            && e.getUserAccount().getUsername().equalsIgnoreCase(currentUserAccount.getUsername()))
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                return new Survey(
                        existing.getId(),
                        existing.getCreatedAt(),
                        existing.getCompletedStatus(),
                        currentUserAccount,
                        diagnosticId
                );
            }

            // Crear nueva encuesta para el usuario actual
            DiscapacitySurveyEntity newSurvey = new DiscapacitySurveyEntity();

            // ✅ Buscar usuario con ignore-case (reemplazo del findByUsername)
            UserEntity user = userRepository.findByUsernameIgnoreCase(currentUserAccount.getUsername())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Usuario no encontrado: " + currentUserAccount.getUsername()));

            newSurvey.setUserAccount(user);
            newSurvey.setCreatedAt(new Date());
            newSurvey.setCompletedStatus(false);
            newSurvey.setName("Inclusión Laboral");
            newSurvey.setDescription("Encuesta sobre inclusión laboral de personas con discapacidad");

            DiscapacitySurveyEntity saved = discapacitySurveyRepository.save(newSurvey);

            return new Survey(
                    saved.getId(),
                    saved.getCreatedAt(),
                    saved.getCompletedStatus(),
                    currentUserAccount,
                    diagnosticId
            );
        }

        // Resto de diagnósticos
        SurveyEntity entity = surveyRepository.getSurveyByUserAndDiagnosticId(
                currentUserAccount.getUsername(), diagnosticId
        );

        Survey survey = surveyMapper.mapSurveyEntityToSurveyModel(entity, currentUserAccount, diagnosticId);

        if (survey.getId() == 0) {
            survey.setId(saveSurvey(survey));
        }

        return survey;
    }

    @Override
    public Survey getSurveyById(int id) {
        SurveyEntity entity = surveyRepository.findById(id).orElse(null);
        return entity != null ? surveyMapper.mapSurveyEntityToSurvey(entity) : null;
    }


    public boolean hasSurvey(int diagnosticId){
        UserAccount currentUserAccount = userService.getCurrentUserInfo();
        if (Objects.isNull(surveyRepository.getSurveyByUserAndDiagnosticId(currentUserAccount.getUsername(), diagnosticId))) {
            return false;
        }
        return true;
    }


    @Override
    public List<SurveyItem> getSurveyItems(Survey survey, boolean next, int questionId, int diagnosticId) {
        if (survey == null || survey.getId() == 0) {
            return new ArrayList<>();
        }

        List<SurveyItem> response;

        switch (diagnosticId) {
            case B_PERSON_DIAGNOSTIC:
                response = getBPersonSurveyItem(survey, next, questionId);
                break;
            case ENGAGEMENT_DIAGNOSTIC:
                response = getEngagementSurveyItem(survey, next, questionId);
                break;
            case DISCAPACITY_DIAGNOSTIC:
                Survey userSurvey = getUserSurveyByDiagnostic(diagnosticId);
                if (userSurvey == null) {
                    response = new ArrayList<>();
                } else {
                    List<DiscapacityQuestion> questions = discapacitySurveyService.getDiscapacitySurveyItem(userSurvey, 1);

                    response = questions.stream().map(dq -> {
                        SurveyItem item = new SurveyItem();
                        item.setId(dq.getId());
                        item.setDescription(dq.getDescription());
                        item.setOrder(dq.getOrder());
                        item.setSection(dq.getSection());
                        item.setQuestion(true); // o false según tu lógica
                        item.setSublevel(false); // o true si aplica

                        if (dq.getAnswerOptions() != null) {
                            List<SurveyAnswerOption> options = dq.getAnswerOptions().stream()
                                    .map(opt -> new SurveyAnswerOption(opt.getId(), opt.getText(), opt.getScore()))
                                    .collect(Collectors.toList());
                            item.setAnswerOptions(options);
                        }

                        return item;
                    }).collect(Collectors.toList());
                }
                break;
            default:
                response = new ArrayList<>();
                break;
        }

        return response != null ? response : new ArrayList<>();
    }
    private Survey getUserSurveyByDiagnostic(int diagnosticId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsernameIgnoreCase(username);
        if (!optionalUser.isPresent()) return null;

        UserEntity user = optionalUser.get();

        Optional<DiscapacitySurveyEntity> optionalEntity =
                discapacitySurveyRepository.findTopByUserAccountAndDiagnosticIdOrderByCreatedAtDesc(user, diagnosticId);

        if (!optionalEntity.isPresent()) return null;

        DiscapacitySurveyEntity entity = optionalEntity.get();

        Survey survey = new Survey();
        survey.setId(entity.getId());
        survey.setName(entity.getName());
        survey.setDescription(entity.getDescription());
        survey.setCreatedAt(entity.getCreatedAt());
        survey.setDiagnosticId(entity.getDiagnosticId());

        return survey;
    }

    @Override
    public List<SurveyItem> getSurveyItemsForDiscapacityByUser(UserEntity user, int section) {
        List<DiscapacityQuestion> discapacityQuestions = discapacitySurveyService.getDiscapacitySurveyItemByUser(user, section);

        List<SurveyItem> surveyItems = new ArrayList<>();

        for (DiscapacityQuestion dq : discapacityQuestions) {
            SurveyItem item = new SurveyItem();
            item.setId(dq.getId());
            item.setDescription(dq.getDescription());
            item.setOrder(dq.getOrder());
            item.setSection(dq.getSection());
            item.setQuestion(true);     // Si aplica
            item.setSublevel(false);    // Ajustar según lógica

            if (dq.getAnswerOptions() != null) {
                List<SurveyAnswerOption> options = dq.getAnswerOptions().stream()
                        .map(opt -> new SurveyAnswerOption(opt.getId(), opt.getText(), opt.getScore()))
                        .collect(Collectors.toList());
                item.setAnswerOptions(options);
            }

            surveyItems.add(item);
        }

        return surveyItems;
    }





    private List<SurveyItem> getBPersonSurveyItem(Survey survey, boolean next, int questionId) {
        if (survey.getSurveyDetailList().isEmpty()) {
            return surveyItemservice.getSurveyItemListByClassification(CLASSIFICATION_DIMENSION);
        } else {
            List<SurveyItem> surveyQuestions = surveyItemservice.getSurveyItemQuestionList(B_PERSON_DIAGNOSTIC);

            List<SurveyItem> response = new ArrayList<SurveyItem>();
            SurveyDetail lastSurveyAnswer = survey.getSurveyDetailList().get(survey.getSurveyDetailList().size() - 1);
            int currentPosition = getCurrentPosition(surveyQuestions, questionId, lastSurveyAnswer);

            if (next) {
                SurveyItem currentSurveyItem = surveyQuestions.get(currentPosition);

                if (currentSurveyItem.getClassification().getId() == CLASSIFICATION_DIMENSION) {
                    if (validateDimensionLevelQuestionAmount(survey.getSurveyDetailList())) {
                        return surveyItemservice.getSurveyItemListByClassification(CLASSIFICATION_DIMENSION);
                    }
                }

                boolean checkSubLevels = this.checkSubLevels(currentSurveyItem.getId(), survey.getSurveyDetailList(),
                        getSubLevelQuestion(survey.getSurveyDetailList(), currentSurveyItem.getId()));

                if (checkSubLevels) {
                    if (currentPosition + 1 < surveyQuestions.size()) {
                        int nextQuestionId = surveyQuestions.get(currentPosition + 1).getId();

                        response = surveyItemservice.getSurveyItemListById(nextQuestionId);
                        if (!response.isEmpty() && response.get(0).getSection() == 2) {
                            List<SurveyItem> answers = surveyItemservice.getSurveyItemResponseSectionTwo();
                            for (SurveyItem a : answers) {
                                a.setFather(response.get(0));
                            }
                            response.addAll(answers);
                        }
                    }
                } else {
                    response = surveyItemservice.getSurveyItemListById(currentSurveyItem.getId());
                }
            } else {
                int lastPosition = IntStream.range(0, surveyQuestions.size())
                        .filter(i -> Objects.nonNull(surveyQuestions.get(i)))
                        .filter(i -> questionId == surveyQuestions.get(i).getId())
                        .findFirst()
                        .orElse(-1);

                if (surveyQuestions.get(lastPosition-1).getClassification().getId() == CLASSIFICATION_DIMENSION) {
                    return surveyItemservice.getSurveyItemListByClassification(CLASSIFICATION_DIMENSION);
                }

                response = surveyItemservice.getSurveyItemListById(surveyQuestions.get(lastPosition-1).getId());
                if(!response.isEmpty() && response.get(0).getSection() == 2) {
                    List<SurveyItem> answers = surveyItemservice.getSurveyItemResponseSectionTwo();
                    for (SurveyItem a: answers) {
                        a.setFather(response.get(0));
                    }
                    response.addAll(answers);
                }
            }
            System.out.println(response);
            return response;

        }
    }

    private List<SurveyItem> getEngagementSurveyItem(Survey survey, boolean next, int questionId) {
        List<SurveyItem> response = new ArrayList<SurveyItem>();
        List<SurveyItem> surveyQuestions = surveyItemservice.getSurveyItemQuestionList(ENGAGEMENT_DIAGNOSTIC);
        List<SurveyItem> answers = surveyItemservice.getSurveyItemResponseSectionTwo();
        int currentPosition = 0;
        if (survey.getSurveyDetailList().isEmpty()) {
            currentPosition = -1;
        } else {
            SurveyDetail lastSurveyAnswer = survey.getSurveyDetailList().get(survey.getSurveyDetailList().size() - 1);
            currentPosition = getCurrentPosition(surveyQuestions, questionId, lastSurveyAnswer);

        }
        if (next) {
            if((currentPosition + 1 < surveyQuestions.size() - 1)) {
                SurveyItem currentItem = surveyQuestions.get(currentPosition + 1);
                response.add(currentItem);
                for (SurveyItem a : answers) {
                    a.setFather(currentItem);
                }
                response.addAll(answers);
                if (currentPosition + 2 < surveyQuestions.size()) {
                    SurveyItem secondCurrentItem = surveyQuestions.get(currentPosition + 2);
                    response.add(secondCurrentItem);
                    List<SurveyItem> secondPositionAnswer = answers.stream().map(a -> {
                        SurveyItem si = new SurveyItem(a.getId(), a.getDescription(), secondCurrentItem, a.getClassification());
                        return si;
                    }).collect(Collectors.toList());
                    response.addAll(secondPositionAnswer);
                }
                if (currentPosition + 3 < surveyQuestions.size()) {
                    SurveyItem thirdCurrentItem = surveyQuestions.get(currentPosition + 3);
                    response.add(thirdCurrentItem);
                    List<SurveyItem> thirdPositionAnswer = answers.stream().map(a -> {
                        SurveyItem si = new SurveyItem(a.getId(), a.getDescription(), thirdCurrentItem, a.getClassification());
                        return si;
                    }).collect(Collectors.toList());
                    response.addAll(thirdPositionAnswer);
                }
            }
        } else {
            if (currentPosition-1 >=0) {
                currentPosition-=1;
            }
            SurveyItem currentItem = surveyQuestions.get(currentPosition);
            response.add(currentItem);
            for (SurveyItem a: answers) {
                a.setFather(currentItem);
            }
            response.addAll(answers);
            if (currentPosition - 1 >= 0) {
                SurveyItem secondCurrentItem = surveyQuestions.get(currentPosition - 1);
                response.add(secondCurrentItem);
                List<SurveyItem> secondPositionAnswer = answers.stream().map(a -> {
                    SurveyItem si = new SurveyItem(a.getId(),a.getDescription(),secondCurrentItem, a.getClassification());
                    return si;
                }).collect(Collectors.toList());
                response.addAll(secondPositionAnswer);
            }
            if (currentPosition - 2 >= 0) {
                SurveyItem thirdCurrentItem = surveyQuestions.get(currentPosition - 2);
                response.add(thirdCurrentItem);
                List<SurveyItem> thirdPositionAnswer = answers.stream().map(a -> {
                    SurveyItem si = new SurveyItem(a.getId(),a.getDescription(),thirdCurrentItem, a.getClassification());
                    return si;
                }).collect(Collectors.toList());
                response.addAll(thirdPositionAnswer);
            }
        }
        return response;
    }




    private int getCurrentPosition(List<SurveyItem> surveyQuestions, int questionId, SurveyDetail lastSurveyAnswer){
        int currentPosition = 0;
        if (questionId > 0 ){
            currentPosition = IntStream.range(0, surveyQuestions.size())
                    .filter(i -> Objects.nonNull(surveyQuestions.get(i)))
                    .filter(i -> questionId == surveyQuestions.get(i).getId())
                    .findFirst()
                    .orElse(-1);
        } else {
            currentPosition = IntStream.range(0, surveyQuestions.size())
                    .filter(i -> Objects.nonNull(surveyQuestions.get(i)))
                    .filter(i -> lastSurveyAnswer.getQuestion().getId() == surveyQuestions.get(i).getId())
                    .findFirst()
                    .orElse(-1);
        }
        return currentPosition;
    }

    private boolean validateDimensionLevelQuestionAmount(List<SurveyDetail> surveyAnswers){
        List<SurveyItem> dimensionSurveyItem = surveyItemservice.getSurveyItemQuestionListByClassification(CLASSIFICATION_DIMENSION);
        long dimensionQuestionCount = surveyAnswers.stream()
                .filter(detailItem -> detailItem.getQuestion().getClassification().getId() == CLASSIFICATION_DIMENSION)
                .count();

        return dimensionQuestionCount < dimensionSurveyItem.size();
    }
    private boolean checkSubLevels(int currentQuestionId, List<SurveyDetail> surveyAnswers, List<SurveyItem> subLevelQuestion) {
        List<SurveyItemExcluded> surveyItemExcludes = surveyItemExcludedService
                .getSurveyItemExcludedListByQuestionId(currentQuestionId);

        if (!surveyItemExcludes.isEmpty()) {
            SurveyDetail questionResponse = null;
            for (SurveyDetail sd : surveyAnswers) {
                if (sd.getQuestion().getId() == currentQuestionId) {
                    questionResponse = sd;
                }
            }

            boolean checkSublevels = true;
            for (SurveyItemExcluded se : surveyItemExcludes) {
                if (se.getAnswer().getId() == questionResponse.getAnswer().getId()) {
                    checkSublevels = false;
                    break;
                }
            }

            if (checkSublevels) {
                long currentAnswers = surveyAnswers.stream()
                        .filter(sdi -> sdi.getQuestion().getId() == currentQuestionId)
                        .count();

                long subLevelQuestionAmount = subLevelQuestion.stream()
                        .filter(slq -> Boolean.TRUE.equals(slq.getQuestion()))
                        .count();

                return currentAnswers - 1 == subLevelQuestionAmount;
            }
        }
        return true;
    }


    @Override
    public List<SurveyItem> getSubLevelQuestion(List<SurveyDetail> surveyAnswers, int currentQuestionId) {
        if (!surveyAnswers.isEmpty()) {
            List<SurveyItem> response = surveyItemservice.getSurveyItemListByClassificationAndFatherId(WORK_LEVEL_CLASSIFICATION, currentQuestionId);
            response.addAll(surveyItemservice.getSurveyItemAnswerListByClassificationId(WORK_LEVEL_CLASSIFICATION));
            response.addAll(surveyItemservice.getSurveyItemListByClassificationAndFatherId(ORGANIZATION_CLASSIFICATION, currentQuestionId));
            response.addAll(surveyItemservice.getSurveyItemAnswerListByClassificationId(ORGANIZATION_CLASSIFICATION));
            List<SurveyItemExcluded> surveyItemExcludedList = surveyItemExcludedService.getSurveyItemExcludedList();
            List<SurveyDetail> dimensionAnswer = surveyAnswers.stream()
                    .filter(sdi -> sdi.getQuestion().getClassification().getId() == CLASSIFICATION_DIMENSION)
                    .collect(Collectors.toList());

            for (SurveyDetail sd : dimensionAnswer) {
                for (SurveyItemExcluded excluded : surveyItemExcludedList) {
                    if (sd.getQuestion().getId() == excluded.getQuestion().getId()) {
                        if (sd.getAnswer().getId() != excluded.getAnswer().getId()) {
                            response.addAll(surveyItemservice.getSurveyItemListByClassificationAndFatherId(excluded.getClassification().getId(), currentQuestionId));
                            response.addAll(surveyItemservice.getSurveyItemAnswerListByClassificationId(excluded.getClassification().getId()));
                        }
                    }
                }
            }
            return response;
        }
        return new ArrayList<SurveyItem>();
    }
    @Override
    public int saveSurvey(Survey survey) {
        if (survey.getDiagnosticId() == 3) {
            // Inclusión Laboral (Discapacity)
            DiscapacitySurveyEntity entity = new DiscapacitySurveyEntity();
            entity.setCreatedAt(survey.getDate());
            entity.setCompletedStatus(survey.getCompletedStatus());

            UserEntity user = userRepository.findByUsernameIgnoreCase(survey.getUserAccount().getUsername())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Usuario no encontrado: " + survey.getUserAccount().getUsername()));

            entity.setUserAccount(user);
            entity.setName("Inclusión Laboral");
            entity.setDescription("Encuesta sobre inclusión laboral de personas con discapacidad");

            DiscapacitySurveyEntity saved = discapacitySurveyRepository.save(entity);
            return saved.getId();

        } else {
            // Otros diagnósticos
            SurveyEntity entity = new SurveyEntity();
            entity.setDate(survey.getDate());
            entity.setCompletedStatus(survey.getCompletedStatus());

            UserEntity user = userRepository.findByUsernameIgnoreCase(survey.getUserAccount().getUsername())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Usuario no encontrado: " + survey.getUserAccount().getUsername()));

            entity.setUser(user);
            entity.setDiagnosticId(survey.getDiagnosticId());

            SurveyEntity saved = surveyRepository.save(entity);
            return saved.getId();
        }
    }


    @Override
    public int closeSurvey(int surveyId) {
        SurveyEntity entity = surveyRepository.findById(surveyId).orElse(null);

        if (entity != null) {
            entity.setCompletedStatus(true);
            surveyRepository.save(entity);
            return entity.getDiagnosticId(); // <- devolvemos el diagnosticId directamente desde la entidad
        }

        return 0; // o puedes lanzar una excepción si prefieres
    }
    @Override
    public void updateSurvey(Survey survey) {
        if (survey.getDiagnosticId() == 3) {
            // Actualizar encuesta de Inclusión Laboral
            DiscapacitySurveyEntity entity = discapacitySurveyRepository.findById(survey.getId())
                    .orElseThrow(() -> new RepositoryException("Encuesta de Inclusión Laboral no encontrada"));

            entity.setCompletedStatus(survey.getCompletedStatus());
            entity.setCreatedAt(survey.getDate());
            discapacitySurveyRepository.save(entity);
        } else {
            // Actualizar encuesta de otros diagnósticos
            SurveyEntity entity = surveyRepository.findById(survey.getId())
                    .orElseThrow(() -> new RepositoryException("Encuesta no encontrada"));

            entity.setCompletedStatus(survey.getCompletedStatus());
            entity.setDate(survey.getDate());
            surveyRepository.save(entity);
        }
    }


    @Override
    public void saveSurveyDetail(int surveyId, int questionId, int answerId, int subQuestionId, int subAnswerId) {
        SurveyDetail surveyDetail = new SurveyDetail(new SurveyItem(questionId,""),
                new SurveyItem(answerId,""),
                new SurveyItem(subQuestionId,""),
                new SurveyItem(subAnswerId,""));
        Survey survey = new Survey(surveyId);
        surveyDetailRepository.save(surveyDetailMapper.mapSurveyDetailModelToSurveyDetailEntity(survey, surveyDetail));
        log.info("surveydetail succesfully saved");
    }

    public Survey convertToModel(SurveyEntity entity) {
        Survey model = new Survey();
        model.setId(entity.getId());
        model.setDate(entity.getDate());
        model.setCompletedStatus(entity.getCompletedStatus());
        model.setDiagnosticId(entity.getDiagnosticId());

        if (entity.getUser() != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(entity.getUser().getId());
            userAccount.setUsername(entity.getUser().getUsername());
            userAccount.setEmail(entity.getUser().getEmail()); // Asegúrate que lo tenga

            // 🔁 Conversión correcta de OrganizationEntity a Organization
            if (entity.getUser().getOrganization() != null) {
                userAccount.setOrganization(
                        organizationMapper.organizationEntityToOrganization(entity.getUser().getOrganization())
                );
            }

            model.setUserAccount(userAccount);
        }

        return model;
    }



    @Override
    public List<Survey> surveyList(int diagnosticId) {
        UserAccount currentUserAccount = userService.getCurrentUserInfo();

        if (currentUserAccount.getRole().getName().equals("ADMIN")) {

            return surveyMapper.mapSurveyEntityListToSurvey(surveyRepository.getSurveyList(diagnosticId));
        } else if (currentUserAccount.getRole().getName().equals("ORGANIZATION") && Objects.nonNull(currentUserAccount.getOrganization())) {
            return surveyMapper.mapSurveyEntityListToSurvey(surveyRepository.
                    getSurveyOrganizationList(currentUserAccount.getOrganization().getName(), diagnosticId));
        } else {
            return surveyMapper.mapSurveyEntityListToSurvey(surveyRepository.getSurveyListByUser(currentUserAccount.getUsername(), diagnosticId));
        }
    }

    @Override
    public void deleteSurvey(int surveyId) {
        surveyDetailRepository.deleteSurveyDetailBySurveyId(surveyId);
        surveyRepository.delete(new SurveyEntity(surveyId));

    }

    @Override
    public List<Result> grhPractice(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        Integer planningP = surveyDetailRepository.getAmount(PLANNING, surveyId);
        Integer abilityP = surveyDetailRepository.getAmount(ABILITY, surveyId);
        Integer motivationP = surveyDetailRepository.getAmount(MOTIVATION, surveyId);
        Integer opportunityP = surveyDetailRepository.getAmount(OPPORTUNITY, surveyId);

        Integer planningPoints = Objects.nonNull(planningP) ? planningP : 0;
        Integer abilityPoints = Objects.nonNull(abilityP) ? abilityP : 0;
        Integer motivationPoints = Objects.nonNull(motivationP) ? motivationP : 0;
        Integer opportunityPoints = Objects.nonNull(opportunityP) ? opportunityP : 0;

        Classification planningClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(PLANNING));
        Classification abilityClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(ABILITY));
        Classification motivationClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(MOTIVATION));
        Classification opportunityClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(OPPORTUNITY));

        float planningPercentage = getPercentage(planningPoints, planningClassification.getTotalPoints());
        float abilityPercentage = getPercentage(abilityPoints, abilityClassification.getTotalPoints());
        float motivationPercentage = getPercentage(motivationPoints, motivationClassification.getTotalPoints());
        float opportunityPercentage = getPercentage(opportunityPoints, opportunityClassification.getTotalPoints());

        List<Integer> classificationIds = Arrays.asList(PLANNING,ABILITY,MOTIVATION,OPPORTUNITY);
        List<Conclusion> conclusions = conclusionService.getConlusionListByClassificationIds(classificationIds);

        Conclusion planningConclusion = conclusions.stream()
                                    .filter(c -> c.getMinValue() <= planningPoints && c.getMaxValue() >= planningPoints && c.getClassification().getId() == PLANNING)
                                    .findFirst().get();

        Conclusion abilityConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= abilityPoints && c.getMaxValue() >= abilityPoints && c.getClassification().getId() == ABILITY)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion motivationConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= motivationPoints && c.getMaxValue() >= motivationPoints && c.getClassification().getId() == MOTIVATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion oppotunityConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= opportunityPoints && c.getMaxValue() >= opportunityPoints && c.getClassification().getId() == OPPORTUNITY)
                .findFirst()
                .orElse(new Conclusion());


        result.add(new Result(PLANNING_DESCRIPTION, planningPoints, planningPercentage, planningConclusion.getDescription()));
        result.add(new Result(ABILITY_DESCRIPTION, abilityPoints, abilityPercentage, abilityConclusion.getDescription()));
        result.add(new Result(MOTIVATION_DESCRIPTION, motivationPoints, motivationPercentage, motivationConclusion.getDescription()));
        result.add(new Result(OPPORTUNITY_DESCRIPTION, opportunityPoints, opportunityPercentage, oppotunityConclusion.getDescription()));

        int totalPointGrhPractice = getTotalPoints(planningPoints, abilityPoints, motivationPoints, opportunityPoints, null, null, null, null);
        int totalClassificationPoints = getTotalPoints(planningClassification.getTotalPoints(), abilityClassification.getTotalPoints()
                                        , motivationClassification.getTotalPoints(), opportunityClassification.getTotalPoints(), null, null, null, null);

        result.add(new Result(RESULTS, totalPointGrhPractice, (((float) totalPointGrhPractice/(float) totalClassificationPoints)*100), null));

        return result;

    }

    @Override
    public List<Result> implementationLevels(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        Integer workLevelPoints = surveyDetailRepository.getSubLevelAmount(WORK_LEVEL_CLASSIFICATION, surveyId);
        Integer organizationLevelPoints = surveyDetailRepository.getSubLevelAmount(ORGANIZATION_CLASSIFICATION, surveyId);
        Integer technologyLevel = surveyDetailRepository.getSubLevelAmount(TECHNOLOGY_CLASSIFICATION, surveyId);
        Integer sustainabilityLevel = surveyDetailRepository.getSubLevelAmount(SUSTAINABILITY_CLASSIFICATION, surveyId);
        Integer ethicsGrhLevel = surveyDetailRepository.getSubLevelAmount(ETHICS_GRH_CLASSIFICATION, surveyId);
        Integer internationalGrhLevel = surveyDetailRepository.getSubLevelAmount(INTERNATIONAL_GRH_CLASSIFICATION, surveyId);

        Integer technologyLevelPoints = Objects.isNull(technologyLevel)? 0: technologyLevel;
        Integer sustainabilityLevelPoints = Objects.isNull(sustainabilityLevel)? 0: sustainabilityLevel;
        Integer ethicsGrhLevelPoints = Objects.isNull(ethicsGrhLevel)? 0: ethicsGrhLevel;
        Integer internationalGrhLevelPoints = Objects.isNull(internationalGrhLevel)? 0: internationalGrhLevel;

        Classification workLevelClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(WORK_LEVEL_CLASSIFICATION));
        Classification organizationClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(ORGANIZATION_CLASSIFICATION));
        Classification technologyClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(TECHNOLOGY_CLASSIFICATION));
        Classification sustainabilityClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(SUSTAINABILITY_CLASSIFICATION));
        Classification ethicsGrhClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(ETHICS_GRH_CLASSIFICATION));
        Classification internationalGrhClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(INTERNATIONAL_GRH_CLASSIFICATION));

        float workLevelPercentage = getPercentage(workLevelPoints, workLevelClassification.getTotalPoints());
        float organizationPercentage = getPercentage(organizationLevelPoints, organizationClassification.getTotalPoints());
        float technologyPercentage = getPercentage(technologyLevelPoints, technologyClassification.getTotalPoints());
        float sustainabilityPercentage = getPercentage(sustainabilityLevelPoints, sustainabilityClassification.getTotalPoints());
        float ethicsGrhPercentage = getPercentage(ethicsGrhLevelPoints, ethicsGrhClassification.getTotalPoints());
        float internationalGrhPercentage = getPercentage(internationalGrhLevelPoints, internationalGrhClassification.getTotalPoints());

        List<Integer> classificationIds = Arrays.asList(WORK_LEVEL_CLASSIFICATION,ORGANIZATION_CLASSIFICATION,TECHNOLOGY_CLASSIFICATION,SUSTAINABILITY_CLASSIFICATION,
                ETHICS_GRH_CLASSIFICATION, INTERNATIONAL_GRH_CLASSIFICATION, RESULT_INDEX);
        List<Conclusion> conclusions = conclusionService.getConlusionListByClassificationIds(classificationIds);

        Conclusion workConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= workLevelPoints && c.getMaxValue() >= workLevelPoints && c.getClassification().getId() == WORK_LEVEL_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion organizationConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= organizationLevelPoints && c.getMaxValue() >= organizationLevelPoints && c.getClassification().getId() == ORGANIZATION_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion technologyConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= technologyLevelPoints && c.getMaxValue() >= technologyLevelPoints && c.getClassification().getId() == TECHNOLOGY_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion sustainabilityConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= sustainabilityLevelPoints && c.getMaxValue() >= sustainabilityLevelPoints && c.getClassification().getId() == SUSTAINABILITY_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion ethicsConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= ethicsGrhLevelPoints && c.getMaxValue() >= ethicsGrhLevelPoints && c.getClassification().getId() == ETHICS_GRH_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());

        Conclusion internationalConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= internationalGrhLevelPoints && c.getMaxValue() >= internationalGrhLevelPoints && c.getClassification().getId() == INTERNATIONAL_GRH_CLASSIFICATION)
                .findFirst()
                .orElse(new Conclusion());


        result.add(new Result(WORK_DESCRIPTION, workLevelPoints, workLevelPercentage, workConclusion.getDescription()));
        result.add(new Result(ORGANIZATION_DESCRIPTION, organizationLevelPoints, organizationPercentage, organizationConclusion.getDescription()));
        result.add(new Result(TECHNOLOGY_DESCRIPTION, technologyLevelPoints, technologyPercentage, technologyConclusion.getDescription()));
        result.add(new Result(SUSTAINABILITY_DESCRIPTION, sustainabilityLevelPoints, sustainabilityPercentage, sustainabilityConclusion.getDescription()));
        result.add(new Result(ETHICS_GRH_DESCIPTION, ethicsGrhLevelPoints, ethicsGrhPercentage, ethicsConclusion.getDescription()));
        result.add(new Result(INTERNATIONAL_GRH_DESCRIPTION, internationalGrhLevelPoints, internationalGrhPercentage, internationalConclusion.getDescription()));


        int totalImplementationLevelsPoints = getTotalPoints(workLevelPoints, organizationLevelPoints, technologyLevelPoints, sustainabilityLevelPoints,
                ethicsGrhLevelPoints, internationalGrhLevelPoints, null, null);

        int totalImplementationLevelsClassificationPoints = getTotalPoints(workLevelClassification.getTotalPoints(), organizationClassification.getTotalPoints(),
                technologyClassification.getTotalPoints(), sustainabilityClassification.getTotalPoints(), ethicsGrhClassification.getTotalPoints(),
                internationalGrhClassification.getTotalPoints(), null, null);

        Conclusion resultsConclusion = conclusions.stream()
                .filter(c -> c.getMinValue() <= totalImplementationLevelsPoints && c.getMaxValue() >= totalImplementationLevelsPoints && c.getClassification().getId() == RESULT_INDEX)
                .findFirst()
                .orElse(new Conclusion());

        result.add(new Result(RESULTS, totalImplementationLevelsPoints,
                        (((float) totalImplementationLevelsPoints/(float) totalImplementationLevelsClassificationPoints)*100), resultsConclusion.getDescription()));

        return result;
    }


    @Override
    public List<Result> directTrait(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        Integer participationIndexPoints = surveyDetailRepository.getSecondSectionAmount(PARTICIPATION_INDEX_CLASSIFICATION, surveyId);
        Integer consistencyIndexPoints = surveyDetailRepository.getSecondSectionAmount(CONSISTENCY_INDEX_CLASSIFICATION, surveyId);
        Integer adaptabilityIndexIndexPoints = surveyDetailRepository.getSecondSectionAmount(ADAPTABILITY_INDEX_CLASSIFICATION, surveyId);
        Integer missionIndexPoints = surveyDetailRepository.getSecondSectionAmount(MISSION_INDEX_CLASSIFICATION, surveyId);

        if(Objects.nonNull(participationIndexPoints) || Objects.nonNull(consistencyIndexPoints) || Objects.nonNull(adaptabilityIndexIndexPoints) || Objects.nonNull(missionIndexPoints)) {

            Classification participationIndexClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(PARTICIPATION_INDEX_CLASSIFICATION));
            Classification consistencyIndexClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(CONSISTENCY_INDEX_CLASSIFICATION));
            Classification adaptabilityIndexClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(ADAPTABILITY_INDEX_CLASSIFICATION));
            Classification missionIndexClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(MISSION_INDEX_CLASSIFICATION));

            float participationIndexPercentage = getPercentage(participationIndexPoints, participationIndexClassification.getTotalPoints());
            float consistencyIndexPercentage = getPercentage(consistencyIndexPoints, consistencyIndexClassification.getTotalPoints());
            float adaptabilityIndexPercentage = getPercentage(adaptabilityIndexIndexPoints, adaptabilityIndexClassification.getTotalPoints());
            float missionIndexPercentage = getPercentage(missionIndexPoints, missionIndexClassification.getTotalPoints());

            List<Integer> classificationIds = Arrays.asList(PARTICIPATION_INDEX_CLASSIFICATION, CONSISTENCY_INDEX_CLASSIFICATION,
                    ADAPTABILITY_INDEX_CLASSIFICATION, MISSION_INDEX_CLASSIFICATION);
            List<Conclusion> conclusions = conclusionService.getConlusionListByClassificationIds(classificationIds);

            Conclusion participationConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= participationIndexPoints && c.getMaxValue() >= participationIndexPoints && c.getClassification().getId() == PARTICIPATION_INDEX_CLASSIFICATION)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion consistencyConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= consistencyIndexPoints && c.getMaxValue() >= consistencyIndexPoints && c.getClassification().getId() == CONSISTENCY_INDEX_CLASSIFICATION)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion adaptabilityConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= adaptabilityIndexIndexPoints && c.getMaxValue() >= adaptabilityIndexIndexPoints && c.getClassification().getId() == ADAPTABILITY_INDEX_CLASSIFICATION)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion missionConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= missionIndexPoints && c.getMaxValue() >= missionIndexPoints && c.getClassification().getId() == MISSION_INDEX_CLASSIFICATION)
                    .findFirst()
                    .orElse(new Conclusion());

            result.add(new Result(PARTICIPATION_INDEX_DESCRIPTION, participationIndexPoints, participationIndexPercentage, participationConclusion.getDescription()));
            result.add(new Result(CONSISTENCY_INDEX_DESCRIPTION, consistencyIndexPoints, consistencyIndexPercentage, consistencyConclusion.getDescription()));
            result.add(new Result(ADAPTABILITY_INDEX_DESCRIPTION, adaptabilityIndexIndexPoints, adaptabilityIndexPercentage, adaptabilityConclusion.getDescription()));
            result.add(new Result(MISSION_INDEX_DESCRIPTION, missionIndexPoints, missionIndexPercentage, missionConclusion.getDescription()));
        }
        return result;
    }

    @Override
    public List<Result> combinedTrait(List<Result> directTrait) {

        List<Result> result =  new ArrayList<Result>();

        if(!directTrait.isEmpty()) {
            int externalPoints = directTrait.stream()
                    .filter(r -> r.getDescription().equals(ADAPTABILITY_INDEX_DESCRIPTION) || r.getDescription().equals(MISSION_INDEX_DESCRIPTION))
                    .mapToInt(r -> Objects.nonNull(r.getPoints()) ? r.getPoints() : 0)
                    .sum();

            int internalPoints = directTrait.stream()
                    .filter(r -> r.getDescription().equals(PARTICIPATION_INDEX_DESCRIPTION) || r.getDescription().equals(CONSISTENCY_INDEX_DESCRIPTION))
                    .mapToInt(r -> Objects.nonNull(r.getPoints()) ? r.getPoints() : 0)
                    .sum();

            int stabilityPoints = directTrait.stream()
                    .filter(r -> r.getDescription().equals(CONSISTENCY_INDEX_DESCRIPTION) || r.getDescription().equals(ADAPTABILITY_INDEX_DESCRIPTION))
                    .mapToInt(r -> Objects.nonNull(r.getPoints()) ? r.getPoints() : 0)
                    .sum();

            int changePoints = directTrait.stream()
                    .filter(r -> r.getDescription().equals(PARTICIPATION_INDEX_DESCRIPTION) || r.getDescription().equals(ADAPTABILITY_INDEX_DESCRIPTION))
                    .mapToInt(r -> Objects.nonNull(r.getPoints()) ? r.getPoints() : 0)
                    .sum();

            List<Integer> classificationIds = Arrays.asList(EXTERNAL_INDEX, INTERNAL_INDEX, STABILITY_INDEX, CHANGE_INDEX);
            List<Conclusion> conclusions = conclusionService.getConlusionListByClassificationIds(classificationIds);

            Conclusion externalConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= externalPoints && c.getMaxValue() >= externalPoints && c.getClassification().getId() == EXTERNAL_INDEX)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion internalConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= internalPoints && c.getMaxValue() >= internalPoints && c.getClassification().getId() == INTERNAL_INDEX)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion stabilityConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= stabilityPoints && c.getMaxValue() >= stabilityPoints && c.getClassification().getId() == STABILITY_INDEX)
                    .findFirst()
                    .orElse(new Conclusion());

            Conclusion changeConclusion = conclusions.stream()
                    .filter(c -> c.getMinValue() <= changePoints && c.getMaxValue() >= changePoints && c.getClassification().getId() == CHANGE_INDEX)
                    .findFirst()
                    .orElse(new Conclusion());

            result.add(new Result(EXTERNAL_DESCRIPTION, externalPoints,
                    (((float) externalPoints / (float) 30) * 100), externalConclusion.getDescription()));
            result.add(new Result(INTERNAL_DESCRIPTION, internalPoints,
                    (((float) internalPoints / (float) 30) * 100), internalConclusion.getDescription()));
            result.add(new Result(STABILITY_DESCRIPTION, stabilityPoints,
                    (((float) stabilityPoints / (float) 30) * 100), stabilityConclusion.getDescription()));
            result.add(new Result(CHANGE_DESCRIPTION, changePoints,
                    (((float) changePoints / (float) 30) * 100), changeConclusion.getDescription()));
        }
        return result;
    }

    @Override
    public List<Result> organizationalCulture(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        Integer clarityP = surveyDetailRepository.getSecondSectionAmount(CLARITY_CLASSIFICATION, surveyId);
        Integer localDirectionCongruencyP = surveyDetailRepository.getSecondSectionAmount(LOCAL_DIRECTION_CONGRUENCY_CLASSIFICATION, surveyId);
        Integer highDirectionCongruencyP = surveyDetailRepository.getSecondSectionAmount(HIGH_DIRECTION_CONGRUENCY_CLASSIFICATION, surveyId);
        Integer feasibilityP = surveyDetailRepository.getSecondSectionAmount(FEASIBILITY_CLASSIFICATION, surveyId);
        Integer supportP = surveyDetailRepository.getSecondSectionAmount(SUPPORT_CLASSIFICATION, surveyId);
        Integer transparencyP = surveyDetailRepository.getSecondSectionAmount(TRANSPARENCY_CLASSIFICATION, surveyId);
        Integer dialogP = surveyDetailRepository.getSecondSectionAmount(DIALOG_CLASSIFICATION, surveyId);
        Integer stimulusP = surveyDetailRepository.getSecondSectionAmount(STIMULUS_CLASSIFICATION, surveyId);

        if(Objects.nonNull(clarityP) && Objects.nonNull(stimulusP)) {

            Integer clarityPoints = Objects.nonNull(clarityP) ? clarityP : 0;
            Integer localDirectionCongruencyPoints = Objects.nonNull(localDirectionCongruencyP) ? localDirectionCongruencyP : 0;
            Integer highDirectionCongruencyPoints = Objects.nonNull(highDirectionCongruencyP) ? highDirectionCongruencyP : 0;
            Integer feasibilityPoints = Objects.nonNull(feasibilityP) ? feasibilityP : 0;
            Integer supportPoints = Objects.nonNull(supportP) ? supportP : 0;
            Integer transparencyPoints = Objects.nonNull(transparencyP) ? transparencyP : 0;
            Integer dialogPoints = Objects.nonNull(dialogP) ? dialogP : 0;
            Integer stimulusPoints = Objects.nonNull(stimulusP) ? stimulusP : 0;

            Classification clarityClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(CLARITY_CLASSIFICATION));
            Classification localDirectionCongruencyClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(LOCAL_DIRECTION_CONGRUENCY_CLASSIFICATION));
            Classification highDirectionCongruencyClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(HIGH_DIRECTION_CONGRUENCY_CLASSIFICATION));
            Classification feasibilityClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(FEASIBILITY_CLASSIFICATION));
            Classification supportClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(SUPPORT_CLASSIFICATION));
            Classification transparencyClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(TRANSPARENCY_CLASSIFICATION));
            Classification dialogClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(DIALOG_CLASSIFICATION));
            Classification stimulusClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(STIMULUS_CLASSIFICATION));

            float clarityPercentage = getPercentage(clarityPoints, clarityClassification.getTotalPoints());
            float localDirectionCongruencyPercentage = getPercentage(localDirectionCongruencyPoints, localDirectionCongruencyClassification.getTotalPoints());
            float highDirectionCongruencyPercentage = getPercentage(highDirectionCongruencyPoints, highDirectionCongruencyClassification.getTotalPoints());
            float feasibilityPercentage = getPercentage(feasibilityPoints, feasibilityClassification.getTotalPoints());
            float supportPercentage = getPercentage(supportPoints, supportClassification.getTotalPoints());
            float transparencyPercentage = getPercentage(transparencyPoints, transparencyClassification.getTotalPoints());
            float dialogPercentage = getPercentage(dialogPoints, dialogClassification.getTotalPoints());
            float stimulusPercentage = getPercentage(stimulusPoints, stimulusClassification.getTotalPoints());


            result.add(new Result(CLARITY_DESCRIPTION, clarityPoints, clarityPercentage, ""));
            result.add(new Result(LOCAL_DIRECTION_CONGRUENCY_DESCRIPTION, localDirectionCongruencyPoints, localDirectionCongruencyPercentage, ""));
            result.add(new Result(HIGH_DIRECTION_CONGRUENCY_DESCRIPTION, highDirectionCongruencyPoints, highDirectionCongruencyPercentage, ""));
            result.add(new Result(FEASIBILITY_DESCRIPTION, feasibilityPoints, feasibilityPercentage, ""));
            result.add(new Result(SUPPORT_DESCRIPTION, supportPoints, supportPercentage, ""));
            result.add(new Result(TRANSPARENCY_DESCRIPTION, transparencyPoints, transparencyPercentage, ""));
            result.add(new Result(DIALOG_DESCRIPTION, dialogPoints, dialogPercentage, ""));
            result.add(new Result(STIMULUS_DESCRIPTION, stimulusPoints, stimulusPercentage, ""));

            int totalPointGrhPractice = getTotalPoints(clarityPoints, localDirectionCongruencyPoints, highDirectionCongruencyPoints, feasibilityPoints,
                    supportPoints, transparencyPoints, dialogPoints, stimulusPoints);
            int totalClassificationPoints = getTotalPoints(clarityClassification.getTotalPoints(), localDirectionCongruencyClassification.getTotalPoints(),
                    highDirectionCongruencyClassification.getTotalPoints(), feasibilityClassification.getTotalPoints(), supportClassification.getTotalPoints(),
                    transparencyClassification.getTotalPoints(), dialogClassification.getTotalPoints(),
                    stimulusClassification.getTotalPoints());

            result.add(new Result(RESULTS, totalPointGrhPractice, (((float) totalPointGrhPractice / (float) totalClassificationPoints) * 100), null));
        }
        return result;
    }
/*
    @Override
    public List<Result> organizationPersonAdjust(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        List<SurveyDetail> organizationPersonAnswer = surveyDetailMapper
                .mapSurveyDetailEntityListToSurveyDetail(surveyDetailRepository.getByClassificationAndSurveyId(ORGANIZATION_PERSON_ADJUST_CLASSIFICATION, surveyId));
        Classification organizationPersonClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(ORGANIZATION_PERSON_ADJUST_CLASSIFICATION
        ));
        int totalPointOrganizationPersonAdjust = 0;
        for (SurveyDetail sd: organizationPersonAnswer) {
            Integer organizationPersonPoints = Objects.nonNull(sd.getAnswer().getScore()) ? sd.getAnswer().getScore() : 0;
            float organizationPersonPercentage = getPercentage(organizationPersonPoints, organizationPersonClassification.getTotalPoints());
            totalPointOrganizationPersonAdjust+= organizationPersonPoints;
            result.add(new Result(ORGANIZATION_PERSON_DESCRIPTION, organizationPersonPoints, organizationPersonPercentage, ""));
        }

        result.add(new Result(RESULTS, totalPointOrganizationPersonAdjust, (((float) totalPointOrganizationPersonAdjust/(float) organizationPersonClassification.getTotalPoints())*100), null));

        return result;
    }

    @Override
    public List<Result> employeeEngagement(int surveyId) {
        List<Result> result =  new ArrayList<Result>();

        Integer emotionalP = surveyDetailRepository.getSecondSectionAmount(EMOTIONAL_ENGAGEMENT_CLASSIFICATION, surveyId);
        Integer cognitiveP = surveyDetailRepository.getSecondSectionAmount(COGNITIVE_ENGAGEMENT_CLASSIFICATION, surveyId);
        Integer behaviouralP = surveyDetailRepository.getSecondSectionAmount(BEHAVIORAL_ENGAGEMENT_CLASSIFICATION, surveyId);

        Integer emotionalPoints = Objects.nonNull(emotionalP) ? emotionalP : 0;
        Integer cognitivePoints = Objects.nonNull(cognitiveP) ? cognitiveP : 0;
        Integer behaviouralPoints = Objects.nonNull(behaviouralP) ? behaviouralP : 0;

        Classification emotionalClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(EMOTIONAL_ENGAGEMENT_CLASSIFICATION));
        Classification cognitiveClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(COGNITIVE_ENGAGEMENT_CLASSIFICATION));
        Classification behaviouralClassification = classificationMapper.mapClassificationEntityToClassification(classificationRepository.getById(BEHAVIORAL_ENGAGEMENT_CLASSIFICATION));

        float emotionalPercentage = getPercentage(emotionalPoints, emotionalClassification.getTotalPoints());
        float cognitivePercentage = getPercentage(cognitivePoints, cognitiveClassification.getTotalPoints());
        float behaviouralPercentage = getPercentage(behaviouralPoints, behaviouralClassification.getTotalPoints());

        result.add(new Result(EMOTIONAL_ENGAGEMENT_DESCRIPTION, emotionalPoints, emotionalPercentage, ""));
        result.add(new Result(COGNITIVE_ENGAGEMENT_DESCRIPTION, cognitivePoints, cognitivePercentage, ""));
        result.add(new Result(BEHAVIORAL_ENGAGEMENT_DESCRIPTION, behaviouralPoints, behaviouralPercentage,""));

        int totalEmployeeEngagementPoints = getTotalPoints(emotionalPoints, cognitivePoints, behaviouralPoints, null, null, null, null, null);
        int totalClassificationPoints = getTotalPoints(emotionalClassification.getTotalPoints(), cognitiveClassification.getTotalPoints(),
                behaviouralClassification.getTotalPoints(), null, null, null, null, null);

        result.add(new Result(RESULTS, totalEmployeeEngagementPoints, (((float) totalEmployeeEngagementPoints/(float) totalClassificationPoints)*100), null));

        return result;
    }
*/

    private float  getPercentage(Integer points, Integer classificationPoints) {
       return ((Objects.nonNull(points) ? (float)points : 0)/(float)classificationPoints)*100;
    }

    private int getTotalPoints(Integer points1, Integer points2, Integer points3, Integer points4, Integer points5, Integer points6, Integer points7, Integer points8) {
        return (Objects.nonNull(points1) ? points1 : 0) + (Objects.nonNull(points2) ? points2 : 0)
                + (Objects.nonNull(points3) ? points3 : 0) + (Objects.nonNull(points4) ? points4 : 0)
                + (Objects.nonNull(points5) ? points5 : 0) + (Objects.nonNull(points6) ? points6 : 0)
                + (Objects.nonNull(points7) ? points7 : 0) + (Objects.nonNull(points8) ? points8 : 0);
    }
    @Override
    public List<SurveyItem> getSurveyItemQuestionList(int diagnosticId) {
        return surveyItemRepository.getSurveyItemQuestionList(diagnosticId)
                .stream()
                .map(entity -> convertToModel(entity))  // ✅ Evita ambigüedad con this::convertToModel
                .collect(Collectors.toList());
    }
    private List<SurveyAnswerOption> getAnswerOptionsForQuestion(int questionId) {
        List<SurveyAnswerOptionEntity> entities = surveyAnswerOptionRepository.findBySurveyItemId(questionId);
        return entities.stream()
                .map(option -> new SurveyAnswerOption(option.getId(), option.getText(), option.getScore()))
                .collect(Collectors.toList());
    }

    private SurveyItem convertToModel(SurveyItemEntity entity) {
        if (entity == null) return null;

        SurveyItem model = new SurveyItem();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setDiagnosticId(entity.getDiagnosticId());
        model.setOrder(entity.getOrder());
        model.setQuestion(Boolean.TRUE.equals(entity.getIsQuestion()));
        model.setSublevel(Boolean.TRUE.equals(entity.getIsSublevel()));
        model.setSection(entity.getSection());

        if (entity.getSurveyAnswerOptions() != null && !entity.getSurveyAnswerOptions().isEmpty()) {
            model.setAnswerOptions(
                    entity.getSurveyAnswerOptions().stream()
                            .filter(Objects::nonNull)
                            .map(opt -> {
                                SurveyAnswerOption option = new SurveyAnswerOption();
                                option.setId(opt.getId());
                                option.setText(opt.getText() != null ? opt.getText() : "");
                                return option;
                            })
                            .collect(Collectors.toList())
            );
        }

        return model;
    }

    private SurveyItem convertToSurveyItemModel(SurveyItemEntity entity) {
        SurveyItem model = new SurveyItem();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setDiagnosticId(entity.getDiagnosticId());
        model.setOrder(entity.getOrder());
        model.setQuestion(entity.getIsQuestion());
        model.setSublevel(entity.getIsSublevel());
        model.setSection(entity.getSection());

        if (entity.getSurveyAnswerOptions() != null) {
            model.setAnswerOptions(
                    entity.getSurveyAnswerOptions().stream().map(opt -> {
                        SurveyAnswerOption option = new SurveyAnswerOption();
                        option.setId(opt.getId());
                        option.setText(opt.getText());
                        option.setScore(opt.getScore());
                        return option;
                    }).collect(Collectors.toList())
            );
        }

        return model;
    }

    @Override
    public List<Survey> getSurveyByDiagnosticId(int diagnosticId) {
        // Obtener el usuario autenticado
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar encuestas activas del usuario actual para ese diagnóstico
        List<SurveyEntity> entities = surveyRepository.findActiveByDiagnosticIdAndUserId(currentUser, diagnosticId);

        return entities.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    private SurveyDetailEntity convertToEntity(SurveyDetail detail,
                                               SurveyEntity surveyEntity,
                                               SurveyItemEntity questionEntity,
                                               SurveyItemEntity subQuestionEntity,
                                               SurveyItemEntity answerEntity,
                                               SurveyItemEntity subAnswerEntity) {

        SurveyDetailEntity entity = new SurveyDetailEntity();

        // Establecer la clave compuesta
        if (entity.getKey() == null) {
            entity.setKey(new SurveyDetailEntityKey());
        }
        entity.getKey().setSurveyId(detail.getSurveyId());
        entity.getKey().setQuestionId(detail.getQuestionId());
        entity.getKey().setSubQuestionId(detail.getSubQuestionId());

        // Establecer relaciones
        entity.setSurvey(surveyEntity);
        entity.setQuestion(questionEntity);
        entity.setSubQuestion(subQuestionEntity);

        if (answerEntity != null) {
            entity.setAnswer(answerEntity);
        }
        if (subAnswerEntity != null) {
            entity.setSubAnswer(subAnswerEntity);
        }

        // Si tienes el campo `freeText` en SurveyDetailEntity (agrega si no lo tienes):
        // entity.setFreeText(detail.getFreeText());

        return entity;
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
                        response.setAnswerOption(answer);

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
                        response.setManualText(manualText); // <== esta es la línea corregida
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
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la encuesta con ID: " + surveyId));

        DiscapacityQuestionEntity question = discapacityQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la pregunta con ID: " + questionId));

        DiscapacityResponseEntity response = new DiscapacityResponseEntity();
        response.setSurvey(survey);
        response.setQuestion(question);
        response.setManualText(freeText); // 🔧 Corrección aquí
        response.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        discapacityResponseRepository.save(response);
    }

    @Override
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }
    @Override
    public List<Survey> discapacitySurveyList() {
        List<DiscapacitySurveyEntity> entities = discapacitySurveyRepository.findAll();
        List<Survey> surveys = new ArrayList<>();

        for (DiscapacitySurveyEntity e : entities) {
            Survey survey = new Survey();
            survey.setId(e.getId());
            survey.setDate(e.getCreatedAt());
            survey.setCompletedStatus(e.getCompletedStatus());
            survey.setDiagnosticId(e.getDiagnosticId());  // usa el valor real

            UserEntity userEntity = e.getUserAccount();  // ✅ corregido aquí
            if (userEntity != null) {
                UserAccount userModel = new UserAccount();
                userModel.setId(userEntity.getId());
                userModel.setUsername(userEntity.getUsername());
                userModel.setName(userEntity.getName());
                userModel.setPassword(userEntity.getPassword());
                userModel.setEmail(userEntity.getEmail());
                survey.setUserAccount(userModel);
            }

            surveys.add(survey);
        }

        return surveys;
    }

    private Survey convertToModel(DiscapacitySurveyEntity entity) {
        Survey model = new Survey();
        model.setId(entity.getId());
        model.setDate(entity.getCreatedAt());
        model.setCompletedStatus(entity.getCompletedStatus());

        if (entity.getUserAccount() != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername(entity.getUserAccount().getUsername());
            userAccount.setId(entity.getUserAccount().getId());
            model.setUserAccount(userAccount);
            model.setUserId(entity.getUserAccount().getId());
        }

        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setDiagnosticId(3); // Inclusión laboral

        return model;
    }


}
