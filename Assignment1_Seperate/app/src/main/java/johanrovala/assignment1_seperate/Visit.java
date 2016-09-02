package johanrovala.assignment1_seperate;

/**
 * Created by johanrovala on 02/09/16.
 */
public class Visit {

    int year;
    String country;

    public Visit (int year, String country){
        this.year = year;
        this.country = country;
    }

    public int getYear(){
        return this.year;
    }

    public String getCountry(){
        return this.country;
    }
}
