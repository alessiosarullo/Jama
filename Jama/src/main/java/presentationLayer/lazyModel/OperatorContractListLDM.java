package presentationLayer.lazyModel;

import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import businessLayer.Contract;
import daoLayer.ContractSearchService;
import daoLayer.ResultPagerBean;

@Dependent
public class OperatorContractListLDM extends OperatorContractTableLDM {
	private static final long serialVersionUID = 1L;

	@EJB
	private ContractSearchService contractSearch;

	protected SortOrder sortOrder;


	public OperatorContractListLDM() {
		super();
		this.sortOrder = SortOrder.DESCENDING;
	}


	public SortOrder getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(SortOrder sortOrder) {
		System.out.println("################################################### Setting sort order to " + sortOrder);
		this.sortOrder = sortOrder;
	}


	@Override
	protected ResultPagerBean<Contract> getPager() {
		return contractSearch;
	}

	@Override
	protected void initPager(Map<String, String> filters) {
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Querying");
		contractSearch.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, sortOrder, Agreement.class);
	}


	@Override
	protected void updateFields(String sortField, SortOrder sortOrder, Map<String, String> filters) {
		super.updateFields(sortField, sortOrder, filters);
		setSortOrder(sortOrder);
		System.out.println("Order: " + sortOrder);
	}


	@Override
	protected FilterList initFilterList() {
		FilterList l = super.initFilterList();

		if (sortOrder != null) {
			l.put("sortorder", sortOrder.toString());
		}

		return l;
	}

}
