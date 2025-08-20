package com.ceipa.GRHApp.Controller;

import com.ceipa.GRHApp.Model.UserAccount;
import com.ceipa.GRHApp.Model.Organization;
import com.ceipa.GRHApp.Model.Role;
import com.ceipa.GRHApp.Model.LevelPosition;
import com.ceipa.GRHApp.Service.LevelPositionService;
import com.ceipa.GRHApp.Service.OrganizationService;
import com.ceipa.GRHApp.Service.RoleService;
import com.ceipa.GRHApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping(value="/user")
public class AccountController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LevelPositionService levelPositionService;

    @RequestMapping(value="/userList")
    public String userList(Model model){
        model.addAttribute("userList", userService.getUserList());
        return "user/userList";
    }

    @RequestMapping(value = "/organizationUserList")
    public String organizationUserList(Model model) {
        model.addAttribute("userList", userService.getOrganizationUserList());
        return "user/userList";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(@Valid UserAccount userAccount,
                           @RequestParam(value = "isOrganization", required = true) int isOrganization,
                           Model model) {
        model.addAttribute("userAccount", userAccount);
        try {
            userService.saveUser(userAccount, isOrganization);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return this.userForm(model);
        }
        return (isOrganization == 1) ? organizationUserList(model) : userList(model);
    }

    @RequestMapping(value="/userForm")
    public String userForm(Model model){
        // Usar SIEMPRE "userAccount" como atributo esperado por la vista
        UserAccount userAccount = (UserAccount) model.getAttribute("userAccount");
        if (Objects.isNull(userAccount)) {
            userAccount = new UserAccount();

            // Como los id son int (primitivo) en tu modelo, inicializa con 0
            Organization org = new Organization();
            org.setId(0);
            userAccount.setOrganization(org);

            LevelPosition lp = new LevelPosition();
            lp.setId(0);
            userAccount.setLevelPosition(lp);

            Role role = new Role();
            role.setId(0);
            userAccount.setRole(role);

            model.addAttribute("userAccount", userAccount);
        }

        model.addAttribute("organizationList", organizationService.getOrganizationList());
        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("levelPositionList", levelPositionService.getAll());
        return "user/userForm";
    }

    @RequestMapping(value="/deleteUser")
    public String deleteUser(@RequestParam(value = "username", required = true) String username, Model model){
        try {
            userService.deleteUser(username);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return this.userList(model);
    }

    @RequestMapping(value="/updateUserForm")
    public String updateUserForm(@RequestParam(value = "username", required = true) String username, Model model){
        try{
            UserAccount userAccount = userService.findUser(username);
            // Si alguno viene null y tus ids son int, protégelos con 0
            if (userAccount.getOrganization() == null) {
                Organization org = new Organization(); org.setId(0);
                userAccount.setOrganization(org);
            }
            if (userAccount.getLevelPosition() == null) {
                LevelPosition lp = new LevelPosition(); lp.setId(0);
                userAccount.setLevelPosition(lp);
            }
            if (userAccount.getRole() == null) {
                Role role = new Role(); role.setId(0);
                userAccount.setRole(role);
            }

            model.addAttribute("organizationList", organizationService.getOrganizationList());
            model.addAttribute("roleList", roleService.getRoleList());
            model.addAttribute("levelPositionList", levelPositionService.getAll());
            model.addAttribute("userAccount", userAccount);
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "user/updateUserForm";
    }

    @RequestMapping(value="/updateUser")
    public String updateUser(@Valid UserAccount userAccount, BindingResult result, Model model){
        model.addAttribute("userAccount", userAccount);
        try {
            userService.updateUser(userAccount);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return this.userForm(model);
        }
        return this.userList(model);
    }

    @RequestMapping(value="/updateUserPolicy")
    public String updateUserPolicy(@RequestParam(value = "username", required = true) String username, Model model) {
        userService.updateUserPolicy(username);
        model.addAttribute("userInfo", userService.getCurrentUserInfo());
        return "dashboard";
    }
}
