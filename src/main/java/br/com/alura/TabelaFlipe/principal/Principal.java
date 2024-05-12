package br.com.alura.TabelaFlipe.principal;

import br.com.alura.TabelaFlipe.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/" ;
    private final Scanner ler = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    public void exibeMenu(){
        var menu = """
                *********OPÇÕES*********
                Carro
                Moto
                Caminhão
                Digite um dos automoveis para conculta:
                """;
        System.out.println(menu);
        var opcao = ler.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carro")){
            endereco = URL_BASE + "carros/marcas";
        }else if (opcao.toLowerCase().contains("moto")) {
            endereco = URL_BASE + "motos/marcas";
        }else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
    }
}
