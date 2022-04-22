package dev.vatuu.vertex.config.codec;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import dev.vatuu.vertex.EnumUtils;
import dev.vatuu.vertex.Serializable;

import java.util.Optional;

public record EnumCodec<E extends Enum<E> & Serializable<String>>(Class<E> clazz) implements PrimitiveCodec<E> {

    @Override
    public <T> DataResult<E> read(DynamicOps<T> dynamicOps, T t) {
        DataResult<String> res = dynamicOps.getStringValue(t);
        if(res.error().isPresent() || res.result().isEmpty())
            return DataResult.error("Unable to parse EnumCodec for \"" + clazz.getSimpleName() + "\": " + res.error().get().message());
        Optional<E> value = EnumUtils.getEnum(clazz, e -> e.serialize().equalsIgnoreCase(res.result().get()));
        return value.map(DataResult::success).orElseGet(() -> DataResult.error("Unable to parse EnumCodec for \"" + clazz.getSimpleName() + "\": Unknown enum value \"" + res.result().get() + "\""));
    }

    @Override
    public <T> T write(DynamicOps<T> dynamicOps, E e) {
        return dynamicOps.createString(e.serialize());
    }
}
