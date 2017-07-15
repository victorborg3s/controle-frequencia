package entity;

public abstract class BaseEntity implements Entity {
	
	public String toString() {
		return "M�todo 'toString()' n�o implementado.";
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((obj.getClass() == this.getClass()) &&
				((BaseEntity)obj).getId().equals(this.getId()));
	}
	
}