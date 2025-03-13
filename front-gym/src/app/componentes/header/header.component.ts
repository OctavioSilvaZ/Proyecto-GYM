import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import dayjs from 'dayjs';
import "dayjs/locale/es";
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  nombre: any
  rol: any
  constructor(private authService: AuthService) { }
  ngOnInit(): void {
    this.getHoraActual();
    this.nombre = this.authService.getNombre();
    this.rol = this.authService.getRol();
  }

  hora: any;

  getFechaActual() {
    dayjs.locale('es');
    let fecha = new Date();
    return dayjs(fecha).format("DD") + " de " + dayjs(fecha).format("MMMM") + " " + dayjs(fecha).format("YYYY");
  }

  getHoraActual(): void {
    this.actualizarHora();
    setInterval(() => {
      this.actualizarHora();
    }, 1000);
  }

  private actualizarHora(): void {
    this.hora = dayjs().format('hh:mm:ss A'); // Formato de hora
  }

  methodLogout() {
    return this.authService.methodLogout();
  }
}
