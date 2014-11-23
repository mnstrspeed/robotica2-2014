import java.io.Serializable;


public interface Observer<T> extends Serializable
{
	void update(T value);
}
