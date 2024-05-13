package br.com.alura.TabelaFlipe.principal;

import br.com.alura.TabelaFlipe.model.Dados;
import br.com.alura.TabelaFlipe.model.Modelos;
import br.com.alura.TabelaFlipe.model.Veiculo;
import br.com.alura.TabelaFlipe.service.ConsumoApi;
import br.com.alura.TabelaFlipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/" ;
    private final Scanner ler = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

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

        var marcas = conversor.obterLista(json , Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe a marca para consulta:");
        var codigoMarca = ler.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json,Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite uma parte do nome do carro para ser buscado:");
        var nomeVeiculo = ler.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Informe o código do modelo que deseja pegar as avaliações: ");
        var codigoModelo = ler.nextLine();
        endereco = endereco +"/"+codigoModelo+"/anos";
        json = consumo.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados (json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: "); veiculos.forEach(System.out::println);
    }
}