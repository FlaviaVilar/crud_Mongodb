import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;


public class mongoAppMain {

    public static void main (String[] args){
        System.out.println("Conectando com MongoDB");

        MongoClient client = MongoClients.create("mongodb://localhost");

        System.out.println("Conectando a base test");
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("produtos");


        Scanner menu = new Scanner (System.in);

        while (true) {
            System.out.print("##--Teste Estrutura de Menu--##\n\n");
            System.out.print("|-------------------------------|\n");
            System.out.print("| Opção 1 - Listar produtos     |\n");
            System.out.print("| Opção 2 - Cadastrar produtos  |\n");
            System.out.print("| Opção 3 - Alterar produtis    |\n");
            System.out.print("| Opção 4 - Excluir produtos    |\n");
            System.out.print("| Opção 5 - Sair                |\n");
            System.out.print("|-----------------------------|\n");
            System.out.print("Digite uma opção: ");

            int opcao = menu.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("\nOpção Listar produtos selecionada");

                    System.out.println("Imprimindo Produtos");
                    Iterable<Document> produtos = collection.find();
                    for (Document produto : produtos) {
                        String nome = produto.getString("nome");
                        String descricao = produto.getString("descricao");
                        String valor = produto.getString("valor");
                        String estado = produto.getString("estado");
                        System.out.println(nome + "--" + descricao + "--" + valor + "--" + estado);
                    }
                    break;

                case 2:

                    System.out.print("\nOpção Cadastrar produtos selecionada\n");

                    System.out.println("Cadastrando novo produto");
                    Document doc = new Document("nome", "Prod7")
                            .append("descricao", "Bla bla")
                            .append("valor", "1500.0")
                            .append("estado", "Bla bla");
                    db.getCollection("test").insertOne(doc);

                    collection.insertOne(doc);
                    break;

                case 3:
                    System.out.print("\nOpção Alterar produtos selecionado\n");

                    Document query = new Document();
                    query.append("nome","Prod5");
                    Document setData = new Document();
                    setData.append("valor", "10000.0");
                    Document update = new Document();
                    update.append("$set", setData);
                    //To update single Document
                    collection.updateOne(query, update);
                    break;

                case 4:
                    System.out.print("\nOpção Excluir produtos selecionado\n");

                    collection.deleteOne(new Document("nome", "Prod3"));

                    break;

                default:
                    System.out.print("\nOpção Inválida!");
                    break;

                case 5:
                    System.out.print("\nAté logo!");
                    menu.close();
            }
        }



       /* System.out.println("Lista as colecoes da base teste");
        Iterable<Document> collections = db.listCollections();
        for (Document col: collections) {
            System.out.println(col.get("name"));
        }*/




    }
}
