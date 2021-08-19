package org.lab510.cryptodac.visitor;

public interface Resource {
    public boolean accept(Visitor visitor);
}
