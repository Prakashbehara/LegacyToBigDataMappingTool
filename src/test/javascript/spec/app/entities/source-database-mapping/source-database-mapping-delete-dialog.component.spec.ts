/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseMappingDeleteDialogComponent } from 'app/entities/source-database-mapping/source-database-mapping-delete-dialog.component';
import { SourceDatabaseMappingService } from 'app/entities/source-database-mapping/source-database-mapping.service';

describe('Component Tests', () => {
    describe('SourceDatabaseMapping Management Delete Component', () => {
        let comp: SourceDatabaseMappingDeleteDialogComponent;
        let fixture: ComponentFixture<SourceDatabaseMappingDeleteDialogComponent>;
        let service: SourceDatabaseMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseMappingDeleteDialogComponent]
            })
                .overrideTemplate(SourceDatabaseMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceDatabaseMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDatabaseMappingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
