package nl.hva.backend.models;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}