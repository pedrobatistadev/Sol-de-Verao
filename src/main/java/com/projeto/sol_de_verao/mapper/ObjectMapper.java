package com.projeto.sol_de_verao.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O,D> D parseObject(O origin, Class<D> destiny) {
        return mapper.map(origin, destiny);
    }

    public static <O,D> List<D> parseList(List<O> origin, Class<D> destiny) {
        List<D> destinyDTO = new ArrayList<>();

        for (O data : origin) {
            destinyDTO.add(parseObject(data, destiny));
        }
        return destinyDTO;
    }
}
