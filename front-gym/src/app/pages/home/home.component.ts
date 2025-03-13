import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../componentes/footer/footer.component";
import { HeaderComponent } from "../../componentes/header/header.component";
import { AuthService } from '../../services/auth.service';
import { PersonalService } from '../../services/personal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  imports: [FooterComponent, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  personal!: Array<any>;


  constructor(private authService: AuthService, private personalService: PersonalService) {
    this.authService.methodAuth();
  }


  ngOnInit(): void {
    this.getPersonal();
  }

  getPersonal() {
    this.personalService.getListarPersonal(this.authService.getToken()).subscribe({
      next: data => {
        console.log(data)
        this.personal = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: 'Opss',
          text: "Ocurrio un error al cargar el personal"
        });
      }
    });
  }

}
