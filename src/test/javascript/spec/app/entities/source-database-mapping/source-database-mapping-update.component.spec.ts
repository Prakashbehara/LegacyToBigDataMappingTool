/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseMappingUpdateComponent } from 'app/entities/source-database-mapping/source-database-mapping-update.component';
import { SourceDatabaseMappingService } from 'app/entities/source-database-mapping/source-database-mapping.service';
import { SourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

describe('Component Tests', () => {
    describe('SourceDatabaseMapping Management Update Component', () => {
        let comp: SourceDatabaseMappingUpdateComponent;
        let fixture: ComponentFixture<SourceDatabaseMappingUpdateComponent>;
        let service: SourceDatabaseMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseMappingUpdateComponent]
            })
                .overrideTemplate(SourceDatabaseMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDatabaseMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDatabaseMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceDatabaseMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDatabaseMapping = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceDatabaseMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDatabaseMapping = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
