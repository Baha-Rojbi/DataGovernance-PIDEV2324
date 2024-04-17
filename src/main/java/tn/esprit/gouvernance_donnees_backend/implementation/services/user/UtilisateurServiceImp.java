package tn.esprit.gouvernance_donnees_backend.implementation.services.user;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.VerifyOldPsswordRequest;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Team;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IUtilisateurImp;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.AdresseRepository;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.TeamRepository;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.UtilisateurRepository;

@AllArgsConstructor
@Slf4j
@Service
public class UtilisateurServiceImp implements IUtilisateurImp {

    private UtilisateurRepository utilisateurRepository;
    private AdresseRepository adresseRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private TeamRepository teamRepository;

    @Override
    public Utilisateur getUserInformationByLoggedEmail(String email) {
        Utilisateur user = null;
        try {
            user = utilisateurRepository.findByEmail(email);
        } catch (Exception e) {
            log.info(e.toString());
        }
        return user;

    }

    @Override
    public List<Utilisateur> getPendingUsersRequests() {
        return utilisateurRepository.getPendingUsersRequests();
    }

    @Override
    public Utilisateur affectRoleAndChangeStatus(Long idUtilisateur, Role role, UserStatus userStatus) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = utilisateurRepository.findById(idUtilisateur)
                    .orElseThrow(() -> new Exception("User not found with id: " + idUtilisateur));
            utilisateur.setStatus(userStatus);
            utilisateur.setRole(role);
            String message = "<p>Dear " + utilisateur.getNom() + ",</p>"
                    + "<h1>We hope this message finds you well.</h1>"
                    + "<p>We wanted to inform you of an important update regarding your account on our platform. Our administrators have made some changes to your account status and/or role. These changes are aimed at improving our services and ensuring that your experience with us is optimal.</p>"
                    + "<p>If you have any questions or concerns regarding these changes, please don't hesitate to reach out to our support team. We're here to assist you every step of the way.</p>"
                    + "<h1>Your current status is " + utilisateur.getStatus() + "</h1>"
                    + "<h1>Your current role is " + utilisateur.getRole() + "</h1>"
                    + "<p>Thank you for your continued support and understanding.</p>"
                    + "<p>Best regards,</p>";

            this.SendEmailToUser(utilisateur, message);

            if (utilisateur.getOwnedTeam() == null && role == Role.RESPONSABLE_ANALYSE) {
                try {
                    // Create a team for the user
                    Team team = new Team();
                    team.setName(utilisateur.getEmail() + " Team");
                    team.setMembers(Collections.singletonList(utilisateur)); // Owner is also a member
                    teamRepository.save(team);
                    utilisateur.setOwnedTeam(team);
                    utilisateurRepository.save(utilisateur);
                } catch (Exception ex) {
                    // Handle exception
                }
            }

        } catch (Exception ex) {
            // Handle the exception or rethrow it as needed
            System.out.println("Error occurred while affecting role and changing status: " + ex.getMessage());
        }
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateProfile(Utilisateur user) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur = utilisateurRepository.findById(user.getIdUtilisateur())
                    .orElseThrow(() -> new Exception("User not found with id: " + user.getIdUtilisateur()));

            utilisateur.setNom(user.getNom());
            utilisateur.setPoste(user.getPoste());
            utilisateur.setAvatar(user.getAvatar());
            utilisateur.setNCIN(user.getNCIN());
            utilisateur.setDateNaissance(user.getDateNaissance());
            utilisateur.setDateEmbauche(user.getDateEmbauche());
            utilisateur.setSexe(user.getSexe());
            utilisateur.setNumTel(user.getNumTel());
            utilisateur.setDescription(user.getDescription());
            utilisateur.setSociete(user.getSociete());
            
            this.adresseRepository.save(user.getAdresse());
            utilisateur.setAdresse(user.getAdresse());
        } catch (Exception ex) {
            // Handle the exception or rethrow it as needed
            System.out.println("User not found: " + ex.getMessage());
        }
        return utilisateurRepository.save(utilisateur);

    }

    private void SendEmailToUser(Utilisateur user, String message) {
        try {
            emailService.sendHtmlEmail(user.getEmail(), "Important Update Regarding Your Account", message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean verifyOldPassword(VerifyOldPsswordRequest verifyOldPsswordRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(verifyOldPsswordRequest.getNewPassword(), verifyOldPsswordRequest.getOldPassword());
    }

    @Override
    public Utilisateur updatePassword(Long utilisateurId, String newPassword) {
        // Retrieve the user from the database
        @SuppressWarnings("null")
        Utilisateur user = this.utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Update the user's password
        user.setMotDePasse(encodedPassword);
        return this.utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur addUserToTeamByEmail(String currentUserEmail, String userToAddEmail) throws Exception {
        // Fetch users by email
        Utilisateur currentUser = utilisateurRepository.findByEmail(currentUserEmail);
        Utilisateur userToAdd = utilisateurRepository.findByEmail(userToAddEmail);
    
        // Check if both users exist
        if (currentUser == null) {
            throw new Exception("User not found with email: " + currentUserEmail);
        }
        if (userToAdd == null) {
            throw new Exception("User not found with email: " + userToAddEmail);
        }
    
        // Find the team owned by the current user
        Team team = currentUser.getOwnedTeam();
    
        // Check if the userToAdd is already a member of the team
        if (team.getMembers().contains(userToAdd)) {
            throw new Exception("User with email " + userToAddEmail + " is already a member of the team.");
        }
    
        // Add the user to the team
        team.getMembers().add(userToAdd);
        userToAdd.getTeams().add(team);
    
        teamRepository.save(team);
        utilisateurRepository.save(userToAdd);
        return userToAdd;
    }
    

    @Override
    public List<Utilisateur> getAllApproveUsers(Long UserId, Long ownedTeamId) {
        return this.utilisateurRepository.getAllApproveUsers(UserId,ownedTeamId);
    }

    @Override
    public List<Utilisateur> getOwnedTeamMemebers(Long ownedTeamId) {
        return this.teamRepository.getOwnedTeamMemebers(ownedTeamId);
    }

    @Override
    public Team removeMemberFromTeam(Long teamId, Long memberId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        Utilisateur user = utilisateurRepository.findById(memberId) // Find the user to remove
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + memberId));

        // Remove the team from the user's teams list
        user.getTeams().removeIf(t -> t.getId().equals(teamId));

        // Save the updated user (cascade will persist the change to the team)
        utilisateurRepository.save(user);

        return team; // You can return the team object if needed
    }
}
