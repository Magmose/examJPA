package entity.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "history")
public class History implements Serializable {

  private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String searchHistory;

    public History() {
    }

    public History(String searchHistory) {
        this.searchHistory = searchHistory;
    }

    public Integer getId() {
        return id;
    }

    public String getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(String searchHistory) {
        this.searchHistory = searchHistory;
    }
    
  
}
