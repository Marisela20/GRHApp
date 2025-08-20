package com.ceipa.GRHApp.Controller;

import com.ceipa.GRHApp.Model.*;
import com.ceipa.GRHApp.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired private UserService userService;
    @Autowired private OrganizationService organizationService;
    @Autowired private LevelPositionService levelPositionService;
    @Autowired private EconomicAreaService economicAreaService;
    @Autowired private DiscapacitySurveyService discapacitySurveyService;

    // ====== PAGE BASICS ======
    @GetMapping({"/", "/index"}) public String index(){ return "index"; }
    @GetMapping("/login")       public String login(){ return "login"; }
    @GetMapping("/dashboard")   public String dashboard(Model model){
        model.addAttribute("userInfo", userService.getCurrentUserInfo());
        return "dashboard";
    }
    @GetMapping("/credits")     public String credits(Model model){
        model.addAttribute("userInfo", userService.getCurrentUserInfo());
        return "credits";
    }
    @GetMapping("/forbidden")   public String forbidden(){ return "forbidden"; }

    // ====== COMBOS (se inyectan SIEMPRE al modelo) ======
    @ModelAttribute("levelPositionList")
    public List<LevelPosition> levelPositionList() {
        List<LevelPosition> fromDb = levelPositionService.getAll();
        if (fromDb != null && !fromDb.isEmpty()) return fromDb;
        // Fallback simple (por si la tabla está vacía)
        return Arrays.asList(
                new LevelPosition(1, "Operativo"),
                new LevelPosition(2, "Administrativo sin personal a cargo"),
                new LevelPosition(3, "Mando medio con personal a cargo"),
                new LevelPosition(4, "Gerencia / Dirección"),
                new LevelPosition(5, "Presidencia"),
                new LevelPosition(6, "Junta directiva"),
                new LevelPosition(7, "Docente"),
                new LevelPosition(8, "Otros")
        );
    }

    @ModelAttribute("economicAreaList")
    public List<EconomicArea> economicAreaList() {
        List<EconomicArea> fromDb = economicAreaService.getEconomicAreaList();
        if (fromDb != null && !fromDb.isEmpty()) return fromDb;
        // Fallback simple
        return Arrays.asList(
                new EconomicArea(1, "Agrícola"),
                new EconomicArea(2, "Industrial"),
                new EconomicArea(3, "Servicios"),
                new EconomicArea(4, "Organización híbrida / BIC / Empresa B / Cuarto sector"),
                new EconomicArea(5, "Entidad pública"),
                new EconomicArea(6, "Entidad sin ánimo de lucro"),
                new EconomicArea(7, "Institución de educación")
        );
    }

    // ====== REGISTRO ======
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        UserAccount ua = new UserAccount();

        Organization org = new Organization();
        org.setId(0);

        EconomicArea ea = new EconomicArea();
        ea.setId(0);
        org.setEconomicArea(ea);

        ua.setOrganization(org);

        LevelPosition lp = new LevelPosition();
        lp.setId(0);
        ua.setLevelPosition(lp);

        Role role = new Role();
        role.setId(0);
        ua.setRole(role);

        model.addAttribute("userAccount", ua);
        return "register";
    }

    // IndexController.java  (solo el método POST)
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("userAccount") UserAccount userAccount, Model model) {
        try {
            // 1) Guardar Organización (si tu servicio no lo hace en cascada)
            if (userAccount.getOrganization() != null) {
                organizationService.saveOrganization(userAccount.getOrganization());
            }

            // 2) Defaults del usuario
            userAccount.setAcceptPolicy(false);
            userAccount.setFirstTime(true);
            if (userAccount.getWorkerPosition() == null || userAccount.getWorkerPosition().isEmpty()) {
                userAccount.setWorkerPosition("Usuario");
            }

            // 3) Guardar usuario
            userService.saveUser(userAccount, 0);

            // 4) Intentar crear encuesta inicial, pero NO bloquear el redirect si falla
            try {
                if (userAccount.getUsername() != null) {
                    discapacitySurveyService.getLastSurveyForCurrentUserOrCreate(userAccount.getUsername());
                }
            } catch (Exception surveyEx) {
                // Loguea y sigue
                System.out.println("WARN: no se pudo crear encuesta inicial: " + surveyEx.getMessage());
            }

            // 5) Redirigir al login con mensaje de éxito
            return "redirect:/login?registered=true";

        } catch (Exception e) {
            // Re-render con error y listas repobladas
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            model.addAttribute("levelPositionList", levelPositionService.getAll());
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "register";
        }
    }

}
