package Source;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id",nullable = false)
    private int review_id;
    String comment;
    Date data;
    int rate;

    public Review(){
        super();
    }

    public Review(String c,Date d,int r){
        comment=c;
        data=d;
        rate=r;
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public Date getData() {
        return data;
    }
}
