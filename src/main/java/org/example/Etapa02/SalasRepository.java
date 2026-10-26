package org.example.Etapa02;

import java.util.List;

public abstract class SalasRepository {
    public abstract List<Sala> getAll();
    public abstract Sala getByCode(String codigo);
}
