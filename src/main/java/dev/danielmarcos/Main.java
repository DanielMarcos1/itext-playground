package dev.danielmarcos;

import dev.danielmarcos.model.DocumentEditor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DocumentEditor documentEditor = new DocumentEditor();

        Map<String, String> replacements = new HashMap<>();
        replacements.put("EDITABLE_NOME", "Daniel");
        replacements.put("EDITABLE_CPF", "68423373800");
        replacements.put("EDITABLE_BAIRRO", "Rua da pamonha, 155");
        replacements.put("EDITABLE_CIDADE_ESTADO", "São Paulo SP");
        replacements.put("EDITABLE_CEP", "03311-000");
        replacements.put("EDITABLE_EMAIL", "danielmarcos@gmail.com");
        replacements.put("EDITABLE_TELEFONE", "(11) 99999-9999");
        replacements.put("EDITABLE_VALOR", "1583");

        try {
            String inputFilePath = "src/main/resources/input/contract.docx";
            String outputFilePath = "src/main/resources/output/edited_contract.docx";

            documentEditor.editDocument(inputFilePath, outputFilePath, replacements);

            System.out.println("Documento editado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}