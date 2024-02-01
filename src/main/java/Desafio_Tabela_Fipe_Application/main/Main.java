package Desafio_Tabela_Fipe_Application.main;

import Desafio_Tabela_Fipe_Application.model.*;
import Desafio_Tabela_Fipe_Application.service.ConsumeApi;
import Desafio_Tabela_Fipe_Application.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    Scanner input  = new Scanner(System.in);
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";
    ConsumeApi consume = new ConsumeApi();
    ConvertData converter = new ConvertData();

    public void showMenu() {
        System.out.println("What type of vehicle would you like to search? Cars, Trucks or Bikes");
        String option = input.nextLine();
        String address;
        if (option.toLowerCase().contains("car")) {
            address = BASE_URL + "carros/marcas";
        } else if (option.toLowerCase().contains("bik")) {
            address = BASE_URL + "motos/marcas";
        } else {
            address = BASE_URL + "caminhoes/marcas";
        }
        String json = consume.obtainInfo(address);
        List<Data> data = converter.getList(json, Data.class);

        data.stream()
                .sorted(Comparator.comparing(Data::code))
                        .forEach(System.out::println);

        System.out.println("\nPlease choose a brand by code.");
        try {
            int modelCode = input.nextInt();
            input.nextLine();
            address = address + "/" + modelCode + "/modelos";
            json = consume.obtainInfo(address);
            Models modelsList = converter.getData(json, Models.class);
            System.out.println(modelsList);
            System.out.println("Please enter an excerpt of the model");
            String model = input.nextLine();
            List<Data> filteredModels = modelsList.models().stream()
                    .filter(m -> m.name().contains(model))
                    .sorted(Comparator.comparing(Data::name))
                    .collect(Collectors.toList());
            System.out.println(filteredModels);

            System.out.println("\nPlease choose the model's code");
            modelCode = input.nextInt();
            input.nextLine();
            address = address + "/" + modelCode + "/anos";
            json = consume.obtainInfo(address);
            List<Data> years = converter.getList(json, Data.class);
            List<Vehicle> vehicles = new ArrayList<>();

            for (Data year : years) {
                String addressYear = address + "/" + year.code();
                json = consume.obtainInfo(addressYear);
                Vehicle vehicle = converter.getData(json, Vehicle.class);
                vehicles.add(vehicle);
            }

            System.out.println("\nAll filtered vehicles\n");
            vehicles.forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("Error! Please insert a number!");
        }





    }
}