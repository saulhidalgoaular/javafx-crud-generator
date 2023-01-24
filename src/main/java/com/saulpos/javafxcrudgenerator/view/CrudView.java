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

package com.saulpos.javafxcrudgenerator.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class CrudView {

    private Node mainView;

    private HashMap<String, Node> parameterNodes;

    private TextField searchBox;
    private TableView tableView;
    private Label totalLabel;
    private Node addNewButton;
    private Node editButton;
    private Node deleteButton;
    private Node refreshButton;

    public Node getMainView() {
        return mainView;
    }

    public void setMainView(Node mainView) {
        this.mainView = mainView;
    }

    public HashMap<String, Node> getParameterNodes() {
        return parameterNodes;
    }

    public void setParameterNodes(HashMap<String, Node> parameterNodes) {
        this.parameterNodes = parameterNodes;
    }

    public TextField getSearchBox() {
        return searchBox;
    }

    public void setSearchBox(TextField searchBox) {
        this.searchBox = searchBox;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public Label getTotalLabel() {
        return totalLabel;
    }

    public void setTotalLabel(Label totalLabel) {
        this.totalLabel = totalLabel;
    }

    public Node getAddNewButton() {
        return addNewButton;
    }

    public void setAddNewButton(Node addNewButton) {
        this.addNewButton = addNewButton;
    }

    public Node getEditButton() {
        return editButton;
    }

    public void setEditButton(Node editButton) {
        this.editButton = editButton;
    }

    public Node getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Node deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Node getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(Node refreshButton) {
        this.refreshButton = refreshButton;
    }
}
