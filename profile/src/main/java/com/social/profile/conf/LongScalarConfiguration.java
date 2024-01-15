package com.social.profile.conf;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import graphql.scalars.ExtendedScalars;
import static graphql.introspection.IntrospectionQueryBuilder.build;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;


@Configuration
public class LongScalarConfiguration  {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLLong);
    }

//    @Bean
//    public GraphQLScalarType longScalar() {
//
//        return GraphQLScalarType.newScalar()
//                .name("Long")
//                .description("A custom scalar that handles long values")
//                .coercing(new Coercing() {
//                    @Override
//                    public Object serialize(Object dataFetcherResult) {
//                        if (dataFetcherResult instanceof Long) {
//                            return dataFetcherResult.toString();
//                        } else {
//                            throw new CoercingSerializeException("Expected a Long object.");
//                        }
//                    }
//
//                    @Override
//                    public Object parseValue(Object input) {
//                        try {
//                            if (input instanceof String) {
//                                return Long.parseLong((String) input);
//                            } else {
//                                throw new CoercingParseValueException("Expected a String");
//                            }
//                        } catch (Exception e) {
//                            throw new CoercingParseValueException(String.format("Not a valid long: '%s'.", input), e
//                            );
//                        }
//                    }
//
//                    @Override
//                    public Object parseLiteral(Object input) {
//                        if (input instanceof StringValue) {
//                            try {
//                                return Long.parseLong(((StringValue) input).getValue());
//                            } catch (Exception e) {
//                                throw new CoercingParseLiteralException(e);
//                            }
//                        } else {
//                            throw new CoercingParseLiteralException("Expected a StringValue.");
//                        }
//                    }
//                })
//                .build();
//
////        newRuntimeWiring()
////                .scalar(ExtendedScalars.GraphQLLong)
////                .type(...).build();
//    }
}