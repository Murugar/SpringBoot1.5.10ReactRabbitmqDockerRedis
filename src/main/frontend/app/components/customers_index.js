import _ from "lodash";
import React, {Component} from "react";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import {fetchCustomers, fetchCustomerPage, loadRandomUsers, deleteAUser} from "../actions/index";
import {Grid, Row, Col, Button} from "react-bootstrap";
import Select from 'react-select';
import CustomerList from "./customer_list";
const client = require("../jsfiles/client");
const follow = require("../jsfiles/follow");
const stompClient = require('../jsfiles/websocket-listener');

class CustomerIndex extends Component {

  constructor(props) {
    super(props);
    this.state = {
      customers: [],
      attributes: [],
      pageSize: 10,
      page: 0,
      links: {},
      doFetchData: false,
      doReload: props.doReload,
      isLoading: false,
      defaultEntryFetchSize: 100

    }
    this.onNavigate = this
      .onNavigate
      .bind(this);
    this.updatePageSize = this
      .updatePageSize
      .bind(this);
    this.onDelete = this
      .onDelete
      .bind(this);
    this.loadCustomers = this
      .loadCustomers
      .bind(this);
    this.refreshCurrentPage = this
      .refreshCurrentPage
      .bind(this);  


  }
  // getInitialState() {   return {     isLoading: false   }; }

  componentDidMount() {
    console.log("Inside CcomponentDidMount", this.state);

    this
      .props
      .fetchCustomers(this.state.pageSize, this.state.page);
    this.setState({doReload: false});
     stompClient.register([			
		 	{route: '/topic/newCustomer', callback: this.refreshCurrentPage},
		 	{route: '/topic/deleteCustomer', callback: this.refreshCurrentPage}
		 ]);

  }
  

  componentDidUpdate()
  {
    console.log("Inside componentDidUpdate");
    //this.props.fetchCustomers(this.state.pageSize);

  }
  componentWillMount()
  {
    console.log("Inside componentWillMount");
    // if(this.state.doReload) {   this.props.fetchCustomers(this.state.pageSize);
    // this.setState({doReload:false}); }

  }
  componentWillUpdate()
  {
    console.log("Inside componentWillUpdate");
    // this.props.fetchCustomers(this.state.pageSize);
  }
  componentWillReceiveProps(nextProps)
  {

    console.log("Inside componentWillReceiveProps");

  }

  onNavigate(navLink) {
    this
      .props
      .fetchCustomerPage(navLink);
  }

  onDelete(customer)
  {
    this
      .props
      .deleteAUser(customer.id);
    this
      .props
      .fetchCustomers(this.state.pageSize, this.state.page);

  }

  updatePageSize(pageSize) {
    if (pageSize !== this.state.pageSize) {
      this
        .props
        .fetchCustomers(parseInt(pageSize), this.state.page);
      this.setState({pageSize: pageSize});
    }
  }
  loadCustomers({
    defaultEntryFetchSize = 100
  }) {
    this
      .props
      .loadRandomUsers(this.state.defaultEntryFetchSize);

  }

  onChange(value) {
    this.setState({defaultEntryFetchSize:value});
		
		console.log('Numeric Select value changed to', value);
  }
  
  refreshCurrentPage()
  {
    this
    .props
    .fetchCustomers(this.state.pageSize, this.state.page);
  }
  render() {
    //  this.props.fetchCustomers(this.state.pageSize);
    console.log("Props---:", this.props);
    var numberOfEntriesOptions = [
      {
        value: 50,
        label: '50'
      }, {
        value: 100,
        label: '100'
      }, {
        value: 150,
        label: '150'
      }, {
        value: 200,
        label: '200'
      }, {
        value: 300,
        label: '300'
      }
    ];

    if (_.isEmpty(this.props.customers.customers) || !Array.isArray(this.props.customers.customers)) {
      return (
        <Grid>
          <Row className="show-grid">
            <Col xs={6} md={6}>
            <div className="col-md-3">
             
              </div>
              <div className="col-md-3">
             
             </div>
            </Col>
            <Col xs={6} md={4}><h3><Link to='/customers/new'>Add Customers</Link></h3></Col>
          </Row>
        </Grid>
      ) 
    } else {
     
      return (
        <div className="bg-warning text-primary">
          <Grid>
            <Row className="show-grid">
              <Col xs={6} md={6}>
                <div className="col-md-3">
                 
                </div>
             

              </Col>
              <Col xs={6} md={6}>
              <Link to='/customers/new' >Add Customers</Link></Col>
            </Row>
          </Grid>
          <h1/>
          <CustomerList
            customers={this.props.customers.customers}
            attributes={this.props.customers.attributes}
            links={this.props.customers.links}
            page={this.props.customers.page}
            onNavigate={this.onNavigate}
            onDelete={this.onDelete}
            updatePageSize={this.updatePageSize}
            pageSize={this.state.pageSize}/>
        </div>
      );

    }

  }

}

CustomerIndex.defaultProps = {
  isLoading: false

}
function mapStateToProps(state) {
  // var str = JSON.stringify(state, null, 2);
  console.log("inside customerindex mapStatetoProps", state.customers);
  if (!_.isEmpty(state.customers) && state) {}
  return {customers: state.customers, isLoading: state.isLoading};
}

export default connect(mapStateToProps, {fetchCustomers, fetchCustomerPage, loadRandomUsers, deleteAUser})(CustomerIndex);
