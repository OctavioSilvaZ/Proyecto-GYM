import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from "../../componentes/footer/footer.component";
import swal from 'sweetalert2';
import { TokenService } from '../../services/token.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  imports: [FooterComponent, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  usuario: any;
  constructor(private tokenService: TokenService, private cookieService: CookieService) {
    this.usuario = {
      correo: "",
      password: ""
    };
  }

  enviar() {
    if (this.usuario.correo == 0 || this.usuario.correo == '') {
      swal.fire({
        icon: 'error',
        title: 'Campo vacio',
        text: "El campo E-mail es obligatorio"
      })
      return false;
    }

    if (this.usuario.password == 0 || this.usuario.password == '') {
      swal.fire({
        icon: 'error',
        title: 'Campo vacio',
        text: "El campo Password es obligatorio"
      })
      return false;
    }
    this.tokenService.getToken({ correo: this.usuario.correo, password: this.usuario.password })
      .subscribe({
        next: data => {
          const hours = 1; //tiempo de expiracion del token
          const expirationInMillis = hours * 60 * 60 * 1000;
          this.cookieService.set('gym_token', data.token, 1);
          this.cookieService.set('gym_estado', data.estado, 1);
          this.cookieService.set('gym_user_id', data.id, 1);
          this.cookieService.set('gym_rol', data.rol, 1)
          this.cookieService.set('gym_nombre', data.nombre, 1);
          window.location.href = "/";

        }, error(error) {
          swal.fire({
            icon: 'error',
            title: 'Opss',
            text: "Credenciales Invalidas"
          });
        }
      });
    return true;
  }

}
