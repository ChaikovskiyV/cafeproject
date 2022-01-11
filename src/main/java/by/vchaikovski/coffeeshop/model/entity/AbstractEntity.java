package by.vchaikovski.coffeeshop.model.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
    protected static final Logger logger = LogManager.getLogger();
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(getClass().getSimpleName())
                .append("{ id = ")
                .append(id)
                .toString();
    }
}