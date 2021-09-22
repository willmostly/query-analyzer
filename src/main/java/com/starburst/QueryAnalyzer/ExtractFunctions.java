package com.starburst.QueryAnalyzer;

import io.trino.sql.tree.Node;
import io.trino.sql.parser.ParsingOptions;
import io.trino.sql.parser.SqlParser;
import io.trino.sql.tree.FunctionCall;
import io.trino.sql.tree.Statement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.trino.sql.parser.ParsingOptions.DecimalLiteralTreatment.AS_DOUBLE;

public class ExtractFunctions
{
    SqlParser parser = new SqlParser();

    public ExtractFunctions(){}

    public List<FunctionSignature> extract(String query)
    {
        List<FunctionSignature> functionSignatures = new ArrayList<>();
        Statement statement = parser.createStatement(query, new ParsingOptions(AS_DOUBLE /* anything */));
        findFunctions(statement, functionSignatures);
        return functionSignatures;
    }

    void findFunctions(Node node, List<FunctionSignature> functionSignatures)
    {
        if (node.getClass() == FunctionCall.class && ((FunctionCall)node).getWindow().isEmpty())
        {
            functionSignatures.add(new FunctionSignature(((FunctionCall)node).getName().toString(), ((FunctionCall)node).getArguments().size()));
        }
        for (Node child : node.getChildren()) {
            findFunctions(child, functionSignatures);
        }
    }

    public static void main(String[] args)
    {
        ExtractFunctions extractor = new ExtractFunctions();
        Set<FunctionSignature> functionSignatures = new HashSet<>();
        for (String query: args){
            if (query.endsWith(";")) {
                functionSignatures.addAll(extractor.extract(query.substring(0, query.length() - 2)));
            } else {
                functionSignatures.addAll(extractor.extract(query));
            }
        }
        for (FunctionSignature signature : functionSignatures) {
            System.out.println(signature.toString());
        }
    }
}
