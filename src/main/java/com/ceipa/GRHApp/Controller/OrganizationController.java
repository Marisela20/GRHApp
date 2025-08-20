package com.ceipa.GRHApp.Controller;

import com.ceipa.GRHApp.Model.Organization;
import com.ceipa.GRHApp.Service.EconomicAreaService;
import com.ceipa.GRHApp.Service.OrganizationService;
import com.ceipa.GRHApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired private OrganizationService organizationService;
    @Autowired private EconomicAreaService economicAreaService;
    @Autowired private UserService userService;

    /* ========= Soporte de fecha dd/MM/yyyy o yyyy-MM-dd ========= */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // LocalDate
        DateTimeFormatter dmy = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) { setValue(null); return; }
                try { setValue(LocalDate.parse(text.trim(), dmy)); return; }
                catch (Exception ignore) {}
                setValue(LocalDate.parse(text.trim(), iso));
            }
            @Override public String getAsText() {
                LocalDate v = (LocalDate) getValue();
                return v != null ? v.format(dmy) : "";
            }
        });
        // java.util.Date (por si tu modelo usa Date)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class,
                new org.springframework.beans.propertyeditors.CustomDateEditor(sdf, true));
    }

    /* ========================= LISTAR ========================= */
    @GetMapping("/organizationList")
    public String organizationList(Model model,
                                   @RequestParam(value = "ok", required = false) String ok,
                                   @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("organizationList", organizationService.getOrganizationList());
        if (ok != null)    model.addAttribute("ok", ok);
        if (error != null) model.addAttribute("error", error);
        return "organization/organizationList";
    }

    /* ========================= CREAR ========================= */
    @GetMapping("/organizationForm")
    public String organizationForm(Model model) {
        model.addAttribute("organization", new Organization());
        model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
        return "organization/organizationForm";
    }

    @PostMapping("/saveOrganization")
    public String saveOrganization(@Valid @ModelAttribute("organization") Organization organization,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "organization/organizationForm";
        }
        try {
            organizationService.saveOrganization(organization);
            ra.addFlashAttribute("ok", "Organización creada correctamente.");
            return "redirect:/organization/organizationList";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo crear la organización: " + e.getMessage());
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "organization/organizationForm";
        }
    }

    /* ========================= ELIMINAR ========================= */
    @GetMapping("/deleteOrganization")
    public String deleteOrganization(@RequestParam("name") String name,
                                     RedirectAttributes ra) {
        try {
            organizationService.deleteOrganization(name);
            ra.addFlashAttribute("ok", "Organización eliminada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/organization/organizationList";
    }

    /* ========================= EDITAR ========================= */
    @GetMapping("/updateOrganizationForm")
    public String updateOrganizationForm(@RequestParam("name") String name,
                                         Model model,
                                         RedirectAttributes ra) {
        try {
            Organization organization = organizationService.findOrganization(name);
            model.addAttribute("organization", organization);
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "organization/updateOrganizationForm"; // o "organization/organizationForm" si prefieres una sola vista
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se pudo cargar la organización: " + e.getMessage());
            return "redirect:/organization/organizationList";
        }
    }

    @PostMapping("/updateOrganization")
    public String updateOrganization(@Valid @ModelAttribute("organization") Organization organization,
                                     BindingResult result,
                                     Model model,
                                     RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "organization/updateOrganizationForm";
        }
        try {
            organizationService.updateOrganization(organization);
            ra.addFlashAttribute("ok", "Organización actualizada.");
            return "redirect:/organization/organizationList";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo actualizar: " + e.getMessage());
            model.addAttribute("economicAreaList", economicAreaService.getEconomicAreaList());
            return "organization/updateOrganizationForm";
        }
    }
}
