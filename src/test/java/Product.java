import com.saulpos.javafxcrudgenerator.annotations.Ignore;

public class Product {

    private String name;

    private String description;

    private Price price;

    private String measuringUnit;

    private Currency currency;

    @Ignore
    private String extraLongDescription;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getExtraLongDescription() {
        return extraLongDescription;
    }

    public void setExtraLongDescription(String extraLongDescription) {
        this.extraLongDescription = extraLongDescription;
    }
}
