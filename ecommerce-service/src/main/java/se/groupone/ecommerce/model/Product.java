package se.groupone.ecommerce.model;

public class Product
{
	private String title, category, baker, description, img;
	private int quantity;
	private double price;

	public Product(String title,
			String category,
			String baker,
			String description,
			String img,
			double price,
			int quantity)
	{
		this.title = title;
		this.category = category;
		this.baker = baker;
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

	public void setBaker(String manufacturer)
	{
		this.baker = manufacturer;
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
		return baker;
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
				", baker=" + baker + ", description=" + description +
				", img=" + img + ", price=" + price + ", quantity=" + quantity + "]";
	}
}
