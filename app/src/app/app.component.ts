import { Component } from '@angular/core';

@Component({
selector: 'app-root',
templateUrl: './app.component.html',
styleUrls: ['./app.component.css']
})
export class AppComponent {

title: string;

constructor() {
    this.title = 'ManageIt';
  }

  get isLogged(): boolean {
    return localStorage.getItem('manageitup_auth') === 'true';
  }
}
