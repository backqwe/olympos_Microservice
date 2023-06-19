package org.olympos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The type Complex bean test.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexBeanTest implements Serializable {

    private DubboTest dubboTest;

    private List<String> idLists;

    private Map<String, String> idMaps;
}
