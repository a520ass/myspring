package org.hibernate.tuple;

import com.hf.utils.core.UserUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.CreationUser;
import org.hibernate.annotations.UpdateUser;

/**
 * Created by 520 on 2017/1/10.
 */
public class UpdateUserGeneration implements AnnotationValueGeneration<UpdateUser> {
    private ValueGenerator<?> generator;

    @Override
    public void initialize(UpdateUser annotation, Class<?> propertyType) {
        if(int.class.isAssignableFrom(propertyType)||Integer.class.isAssignableFrom(propertyType)){
            generator=new ValueGenerator<Integer>(){

                @Override
                public Integer generateValue(Session session, Object owner) {
                    return UserUtils.getUser().getId();
                }
            };
        }
        else if(long.class.isAssignableFrom(propertyType)||Long.class.isAssignableFrom(propertyType)){
            generator=new ValueGenerator<Long>(){

                @Override
                public Long generateValue(Session session, Object owner) {
                    return UserUtils.getUser().getId().longValue();
                }
            };
        }
        else if(String.class.isAssignableFrom(propertyType)){
            generator=new ValueGenerator<String>(){

                @Override
                public String generateValue(Session session, Object owner) {
                    return UserUtils.getUser().getId()+"";
                }
            };
        }
        else {
            throw new HibernateException( "Unsupported property type for generator annotation @CreationUser" );
        }
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        return GenerationTiming.ALWAYS;
    }

    @Override
    public ValueGenerator<?> getValueGenerator() {
        return generator;
    }

    @Override
    public boolean referenceColumnInSql() {
        return false;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        return null;
    }
}
