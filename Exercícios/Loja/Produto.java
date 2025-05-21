public abstract class Produto {
    long codigo;
    String titulo;
    String autor;
    String ano;
    int qtd = 0;

    public Produto (String[] infos) {
        codigo = Long.parseLong(infos[2]);
        titulo = infos[3];
        autor = infos[4];
    }

    public static class Livro extends Produto {
        String editora;
        String edicao;
        String paginas;
        String idioma;

        public Livro (String[] infos) {
            super(infos);
            editora = infos[5];
            ano = infos[6];
            edicao = infos[7];
            paginas = infos[8];
            idioma = infos[9];
        }

        @Override
        public String toString() {
            return "Livro\n" +
                "Código: " + codigo + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Editora: " + editora + "\n" +
                "Edição: " + edicao + "\n" +
                "Ano: " + ano + "\n" +
                "Páginas: " + paginas + "\n" +
                "Idioma: " + idioma;
        }
    }

    public static class CD extends Produto {
        String trilhas;
        String gravadora;

        public CD (String[] infos) {
            super(infos);
            trilhas = infos[5];
            gravadora = infos[6];
            ano = infos[7];
        }

        @Override
        public String toString() {
            return "CD\n" +
                "Código: " + codigo + "\n" +
                "Título: " + titulo + "\n" +
                "Banda: " + autor + "\n" +
                "Gravadora: " + gravadora + "\n" +
                "Ano: " + ano + "\n" +
                "trilhas: " + trilhas;
        }
    }

    public static class DVD extends Produto {
        String idioma;
        String genero;
        String nacionalidade;

        public DVD (String[] infos) {
            super(infos);
            idioma = infos[5];
            genero = infos[6];
            ano = infos[7];
            nacionalidade = infos[8];
        }

        @Override
        public String toString() {
            return "DVD\n" +
                "Código: " + codigo + "\n" +
                "Título: " + titulo + "\n" +
                "Diretor: " + autor + "\n" +
                "Gênero: " + genero + "\n" +
                "Ano: " + ano + "\n" +
                "Nacionalidade: " + nacionalidade + "\n" +
                "Idioma: " + idioma;
        }
    }
}