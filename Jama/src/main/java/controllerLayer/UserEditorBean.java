package controllerLayer;

import java.io.Serializable;
import java.security.GeneralSecurityException;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.Department;
import annotations.TransferObj;
import daoLayer.UserDaoBean;
import usersManagement.User;
import util.Messages;

@Named("userEditor")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserEditorBean implements Serializable {

	private static final long serialVersionUID = -4966124878956728047L;
	@Inject
	private Conversation conversation;

	private User currentUser;
	@Inject
	private UserDaoBean userDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private String password;

	public UserEditorBean() {
		super();
	}

	private void begin() {

		conversation.begin();
	}

	@Remove
	private void close() {

		conversation.end();

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String save() throws GeneralSecurityException {
		currentUser.setPassword(password);
		userDao.create(currentUser);

		close();

		return "home";
	}

	public String cancel() {
		close();
		return "home";
	}

	public String createUser() {
		begin();
		currentUser = new User();
		return "userWiz";
	}

	@Produces
	@RequestScoped
	@TransferObj
	public User getUser() {
		return currentUser;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public Department getSelectedDept() {
		if (!currentUser.getBelongingDepts().isEmpty()) {
			return currentUser.getBelongingDepts().get(0);
		} else
			return null;
	}

	public void setSelectedDept(Department selectedDept) {
		currentUser.addDepartment(selectedDept);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void validatePassword(FacesContext context, UIComponent component,
			Object value) {

		try {
			String password1 = (String) value;
			String password2 = (String) ((UIInput) component
					.findComponent("name")).getValue();

			if (!password1.equals(password2)) {
				throw new ValidatorException(
						Messages.getErrorMessage("err_passwordMismatch"));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_invalidPassword"));
		}
	}

}
