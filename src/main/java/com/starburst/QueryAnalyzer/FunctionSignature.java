package com.starburst.QueryAnalyzer;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FunctionSignature
{


    String name;
    int arguments;

    public FunctionSignature(String name, int arguments)
    {
        this.name = requireNonNull(name);
        this.arguments =  arguments;
    }

    @Override
    public boolean equals(Object object){
        if (object.getClass() != FunctionSignature.class) {
            return false;
        }

        if( name.equals(((FunctionSignature) object).getName()) && arguments == ((FunctionSignature) object).getArguments()) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode()*arguments;
    }

    @Override
    public String toString()
    {
        return "FunctionSignature{" +
                "name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }

    public String getName()
    {
        return name;
    }

    public int getArguments()
    {
        return arguments;
    }
}
