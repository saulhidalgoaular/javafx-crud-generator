/*
 * Copyright (C) 2012-2023 Saúl Hidalgo <saulhidalgoaular at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.saulpos.javafxcrudgenerator;

import com.saulpos.javafxcrudgenerator.model.CrudModel;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.presenter.CrudPresenter;
import com.saulpos.javafxcrudgenerator.sample.Product;
import com.saulpos.javafxcrudgenerator.view.CrudView;
import com.saulpos.javafxcrudgenerator.view.CrudViewGenerator;

public class CrudGenerator<S extends AbstractBean> {

    private CrudGeneratorParameter parameter;

    public CrudGenerator(CrudGeneratorParameter parameter) {
        this.parameter = parameter;
    }

    public CrudPresenter generate() throws Exception {
        final CrudModel<S> model = new CrudModel<>(parameter);
        final CrudView view = new CrudViewGenerator(parameter).generate();

        return new CrudPresenter <S>(model, view);
    }
}
