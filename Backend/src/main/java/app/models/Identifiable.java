package app.models;

public interface Identifiable<T>{
    T getId();
    void setId(T id);
}