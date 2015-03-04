package se.groupone.ecommerce.model;

public class Product
{
	private final int id;
	private String title, category, manufacturer, description, img;
	private int quantity;
	private double price;

	public Product(String title,
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
