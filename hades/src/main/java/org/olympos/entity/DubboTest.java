package org.olympos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DubboTest.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboTest implements Serializable {

    private String id;

    private String name;
}
