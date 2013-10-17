package presentationLayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import businessLayer.AgreementType;
import businessLayer.ChiefScientist;
import businessLayer.Company;
import daoLayer.ChiefScientistDaoBean;
import daoLayer.CompanyDaoBean;

@Named("utilPB")
@Dependent
public class UtilPresentationBean implements Serializable{
	private static final long serialVersionUID = 1L;
	//FIXME usare Identifiable
	@EJB private ChiefScientistDaoBean chiefDaoBean;
	@EJB private CompanyDaoBean companyDaoBean;
	
	public SelectItem[] getAgreementTypeItems(){
		AgreementType[] types = AgreementType.values();
		SelectItem[] result = new SelectItem[types.length];
		for(int i=0; i< types.length; i++){
			result[i] = new SelectItem(types[i], types[i].toString());
		}
		return result;
	}
	
	public SelectItem[] getChiefItems(){
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		return getChiefsFromList(chiefs);
	}
	
	public SelectItem[] getCompanyItems(){
		
		List<Company> companies = companyDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[companies.size()];
		Company current = null;
		for(int i=0; i< companies.size(); i++){
			current = companies.get(i);
			result[i] = new SelectItem(current, current.getName());
		}
		return result;
	}
	
	public SelectItem[] getFilterChiefItems(){
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[chiefs.size()+1];
		result[0] = new SelectItem("", "Tutti"); //FIXME lingua non dinamica. Anche quello sotto
		ChiefScientist current = null;
		for(int i=0; i< chiefs.size(); i++){
			current = chiefs.get(i);
			result[i+1] = new SelectItem(current.getId(), current.getName());
		}
		return result;
	}
	
	public SelectItem[] getFilterCompanyItems(){
		
		List<Company> companies = companyDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[companies.size()+1];
		result[0] = new SelectItem("", "Tutte");
		Company current = null;
		for(int i=0; i< companies.size(); i++){
			current = companies.get(i);
			result[i+1] = new SelectItem(current.getId(), current.getName());
		}
		return result;
	}
	
	public SelectItem[] getChiefsItemsNotIn(Collection<ChiefScientist> list){
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		chiefs.removeAll(list);
		return getChiefsFromList(chiefs);
	}
	
	private SelectItem[] getChiefsFromList(List<ChiefScientist> list){
		SelectItem[] result = new SelectItem[list.size()];
		ChiefScientist current = null;
		for(int i=0; i< list.size(); i++){
			current = list.get(i);
			result[i] = new SelectItem(current, current.getName());
		}
		return result;
	}
	
	
	public UtilPresentationBean() {}

}
