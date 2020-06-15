package com.github.viniciusfcf.ifood.grpc;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import com.github.viniciusfcf.proto.OlaRequest;
import com.github.viniciusfcf.proto.OlaResponse;

import io.smallrye.mutiny.Uni;


// Se quiser trabalhar sem o Mutiny

// @Singleton
// public class MeuOlaService extends com.github.viniciusfcf.proto.OlaServiceGrpc.OlaServiceImplBase {
    
//     AtomicInteger inteiro = new AtomicInteger();

//     @Override
//     public void digaOla(OlaRequest request, StreamObserver<OlaResponse> responseObserver) {
//         String nome = request.getNome();

//         responseObserver.onNext(
//             OlaResponse.newBuilder()
//             .setMensagem("Ola "+nome)
//             .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build());
//         responseObserver.onCompleted();
//     }
    
// }


//Trabalhando com Mutiny

@Singleton
public class MeuOlaService extends com.github.viniciusfcf.proto.MutinyOlaServiceGrpc.OlaServiceImplBase {

    AtomicInteger inteiro = new AtomicInteger();
        @Override
        public Uni<OlaResponse> digaOla(OlaRequest request) {
            OlaResponse response = OlaResponse.newBuilder()
                    .setMensagem("Ola2 "+request.getNome())
                    .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build();
			return Uni.createFrom().item(response);
        }
}