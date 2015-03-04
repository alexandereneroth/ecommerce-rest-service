package se.groupone.ecommerce.model;

public class Product
{
	private final int id;
	private String title, category, manufacturer, description, img;
	private int quantity;
	private double price;

	public Product(int id, ProductParameters params)
	{	
		this.id = id;
		this.title = params.getTitle();
		this.category = params.getCategory();
		this.manufacturer = params.getManufacturer();
		this.description = params.getDescription();
		this.img = params.getImg();
		this.price = params.getPrice();
		this.quantity = params.getQuantity();
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void raiseQuantity(int raise)
	{
		quantity += raise;
	}

	public void decreaseQuantity(int decrease)
	{
		quantity -= decrease;
	}
	
	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCategory()
	{
		return category;
	}

	public String getBaker()
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

	public double getPrice()
	{
		return price;
	}

	public int getQuantity()
	{
		return quantity;
	}

	@Override
	public String toString()
	{
		return "Item [title=" + title + ", category=" + category +
				", baker=" + manufacturer + ", description=" + description +
				", img=" + img + ", price=" + price + ", quantity=" + quantity + "]";
	}
}
