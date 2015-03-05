package se.groupone.ecommerce.model;

public class ProductParameters
{
	private final String title, category, manufacturer, description, img;
	private final int quantity;
	private final double price;

	public ProductParameters(String title,
							 String category,
							 String manufacturer,
							 String description,
							 String img,
							 double price,
							 int quantity)
	{
		this.title = title;
		this.category = category;
		this.manufacturer = manufacturer;
		this.description = description;
		this.img = img;
		this.price = price;
		this.quantity = quantity;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCategory()
	{
		return category;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public String getDescription()
	{
		return description;
	}

	public String getImg()
	{
		return img;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public double getPrice()
	{
		return price;
	}
}