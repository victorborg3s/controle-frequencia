package entity;

public abstract class BaseEntity implements Entity {
	
	public String toString() {
		return "Método 'toString()' não implementado.";
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((obj.getClass() == this.getClass()) &&
				((BaseEntity)obj).getId().equals(this.getId()));
	}
	
}