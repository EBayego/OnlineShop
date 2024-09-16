import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css']
})
export class CustomerFormComponent {

  customer = {
    name: '',
    email: '',
    birthDate: ''
  };

  constructor(private customerService: CustomerService) { }

  register(): void {
    this.customerService.createCustomer(this.customer).subscribe(response => {
      console.log('Customer created successfully!', response);
    }, error => {
      console.error('Error creating customer', error);
    });
  }
}