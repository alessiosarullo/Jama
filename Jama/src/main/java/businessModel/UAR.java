package businessModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UAR {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
}
