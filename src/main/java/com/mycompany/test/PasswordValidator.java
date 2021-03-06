

/**
 * Name:            Passwordvalidator
 * Aufgabe:         Validation des Passworts im Backend
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Sascha Nickel
 */


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="pwdValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object obj) 
            throws ValidatorException {
        
        String pwd = obj.toString();
        FacesMessage fm;
        
        if(pwd.length()>=5)
        {
           fm = new FacesMessage("Passwort formal korrekt!");
           FacesContext.getCurrentInstance().addMessage(uic.getClientId(), fm);
        }
        else
        {
            fm = new FacesMessage("Hinweis: min. 5 Zeichen!");
            throw new ValidatorException(fm);
        }
        
    }

}
