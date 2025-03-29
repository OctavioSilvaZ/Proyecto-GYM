import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../componentes/header/header.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { PersonalService } from '../../services/personal.service';
import { AuthService } from '../../services/auth.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-bonos',
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './bonos.component.html',
  styleUrl: './bonos.component.css'
})
export class BonosComponent implements OnInit {
  personal!: Array<any>;

  constructor(private personalService: PersonalService, private authService: AuthService) {

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
