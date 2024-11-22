package modelo;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private String livro;
    private String usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    // Construtor incluindo o ID
    public Emprestimo(int id, String livro, String usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    // Getter para o ID
    public int getId() {
        return id;
    }

    // Outros getters e setters

    public String getLivro() {
        return livro;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
}
