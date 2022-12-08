package app.models;


import javax.persistence.*;

@Entity
@Table(name = Country.TABLE_NAME)
public class Country implements Identifiable<Integer> {
    public static final String TABLE_NAME = "country";


    public Country() {
    }

    public Country(Integer id, String name, String alpa2, String alpha3, String small) {
        this.id = id;
        this.name = name;
        this.imgSmall = alpa2;
        this.alpha2 = small;
        this.alpha3 = alpha3;
    }

    @Id
    @Column(name = "country_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_small", nullable = false)
    private String imgSmall;

    @Column(name = "alpha2", nullable = false)
    private String alpha2;

    @Column(name = "alpha3", nullable = false)
    private String alpha3;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }
}