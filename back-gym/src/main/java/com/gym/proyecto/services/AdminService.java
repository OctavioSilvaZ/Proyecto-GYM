package com.gym.proyecto.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gym.proyecto.DTO.PersonalDatosResponse;
import com.gym.proyecto.DTO.PersonalListResponse;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;

@Service
public class AdminService {

    @Autowired
    private RolPersonalService rolPersonalService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private EstadoPersonalService estadoPersonalService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private JornadaService jornadaService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public PersonalDatosResponse personalFound(PersonalModel personal) {

        PersonalDatosResponse personalResponse = new PersonalDatosResponse(personal.getId(), personal.getNombre(),
                personal.getApePaterno(), personal.getApeMaterno(),
                personal.getDireccion(), personal.getTelefono(), personal.getCorreo(),
                personal.getEstado().getNombre(), personal.getIne(),
                personal.getHorario().getTipo(), personal.getJornada().getTipo(), personal.getHoras_faltantes(),
                personal.getHoras_extra(), personal.getDias_faltantes(), personal.getBono(), personal.getNoCuenta(),
                personal.getFecha_pago());

        return personalResponse;
    }

    public List<PersonalListResponse> personalAll(List<PersonalModel> personal) {

        List<PersonalListResponse> lista = new ArrayList<>();

        personal.forEach((datos) -> {
            RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(datos.getId());
            if (rol.getId() != 1) {
                lista.add(new PersonalListResponse(datos.getId(), datos.getNombre(), datos.getApePaterno(),
                        datos.getApeMaterno(), datos.getEstado().getNombre(), datos.getHorario().getTipo(),
                        datos.getJornada().getTipo(), datos.getFecha_pago()));
            }
        });

        return lista;
    }

    public PersonalModel personalUpdate(RegisterPersonalRequest request, long id,
            MultipartFile fotoImg, MultipartFile ineImg) throws IOException {
        PersonalModel personal = this.personalService.buscarPorId(id);
        String foto = null;
        String ine = null;

        if (personal != null) {
            personal.setNombre(request.getNombre());
            personal.setApePaterno(request.getApePaterno());
            personal.setApeMaterno(request.getApeMaterno());
            personal.setDireccion(request.getDireccion());
            personal.setTelefono(request.getTelefono());
            personal.setCorreo(request.getCorreo());
            personal.setPassword(this.passwordEncoder.encode(request.getPassword()));
            personal.setEstado(this.estadoPersonalService.buscarId(request.getEstado()));
            personal.setHorario(this.horarioService.buscarId(request.getHorario()));
            personal.setJornada(this.jornadaService.buscarId(request.getJornada()));
            personal.setHoras_faltantes(request.getHorasFaltantes());
            personal.setHoras_extra(request.getHorasExtra());
            personal.setDias_faltantes(request.getDiasFaltantes());
            personal.setBono(request.getBono());
            personal.setNoCuenta(request.getNoCuenta());

            String nombre = personal.getNombre().substring(0, 3); // Primeras 3 letras del nombre
            String apePaterno = personal.getApePaterno().substring(0, 4);
            String apeMaterno = personal.getApeMaterno().substring(0, 4);

            foto = personal.getFoto();
            ine = personal.getIne();

            if (foto == null) {
                foto = nombre + "_" + apePaterno + "_" + apeMaterno + ".png";
            }

            if (ine == null) {
                ine = nombre + "_" + apePaterno + "_" + apeMaterno + "_Ine" + ".pdf";
            }

            personal.setFoto(foto);
            personal.setIne(ine);

            this.personalService.guardarIMG(fotoImg, 0, foto);
            this.personalService.guardarIMG(ineImg, 1, ine);
            this.personalService.guardar(personal, 2);
            return personal;
        } else {
            return null;
        }
    }
}
